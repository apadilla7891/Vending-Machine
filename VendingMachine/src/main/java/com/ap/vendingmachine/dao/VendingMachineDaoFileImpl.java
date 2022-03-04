package com.ap.vendingmachine.dao;

import com.ap.vendingmachine.dto.Snack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private Map<Integer, Snack> vMachine = new HashMap<>();
    private Map<Integer, Snack> tMachine = new HashMap<Integer,Snack>();
    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "inventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String inventoryTextFile){
        INVENTORY_FILE = inventoryTextFile;
    }
    
    
    @Override
    public List<Snack> getAllSnacks() throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        return new ArrayList(vMachine.values());
    }

    @Override
    public Snack getSnack(int id) throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        return vMachine.get(id);
    }

    @Override
    public void updateSnackStock(int id) throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        Snack current = vMachine.get(id);
        int stock = current.getInventory();
        stock--;
        current.setInventory(stock);
        vMachine.replace(id, current);
        writeInventory();
    }
    
    
    private Snack unmarshallSnack(String snackAsText){
    // snackAsText gets a line from the file
    // it is in the following format
    // id::item name::item cost::inventory
    //  [0]     [1]            [2]     [3]     [4]     [5]
        String[] snackTokens = snackAsText.split(DELIMITER);

    // snack id is in index 0 of the array.
        int snackId = Integer.parseInt(snackTokens[0]);

    // create a new snack object using the constructor and extracted id from file
        Snack snackFromFile = new Snack(snackId);

     //using setters, manually set the other values.

    // Index 1 - item name
        snackFromFile.setItemName(snackTokens[1]);

    // Index 2 - item cost
        BigDecimal cost = new BigDecimal(snackTokens[2]);
        snackFromFile.setItemCost(cost);

    // Index 3 - inventory
        int snackInventory = Integer.parseInt(snackTokens[3]);
        snackFromFile.setInventory(snackInventory);
        
    // return created dvd
        return snackFromFile;
    }
    
    private void loadInventory() throws VendingMachinePersistenceException, FileNotFoundException {
        Scanner scanner;

        try {
            // Creates Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            //catches the FileNotFoundException and translates it to the DVDLibraryDaoException created
            throw new VendingMachinePersistenceException("Error DVD library data could not be loaded into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentSnack holds the most recent snack unmarshalled
        Snack currentSnack;
        // Goes through INVENTORY_FILE line by line, decoding each line into a snack object by calling the unmarshallSnack method.
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a DVD
            currentSnack = unmarshallSnack(currentLine);

            // Foloowing what was done earlier we will us the dvd title as the map key for the dvd object.
            vMachine.put(currentSnack.getId(), currentSnack);
        }
        // close scanner
        scanner.close();
    }
    
    private String marshallSnack(Snack aSnack){
        // Turna a snack object into a line of text for our file.
        // it will be in the following format 
        //id::item name::item cost::inventory
        // Start with the id and add delimiter to crate space between each value
        String snackAsText = aSnack.getId() + DELIMITER;
        // item name
        snackAsText += aSnack.getItemName() + DELIMITER;
        // item cost
        snackAsText += aSnack.getItemCost().toString() + DELIMITER;
        // inventory
        snackAsText += aSnack.getInventory();
        return snackAsText;
    }
    
    //Writes all dvds in the library out to a LIBRARY_FILE.  
    private void writeInventory() throws VendingMachinePersistenceException, FileNotFoundException {
        PrintWriter out;
        
        //catches and translates IOExceptions to DVDLibraryDaoExceptions
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException( "Could not save snack data.", e);
        }

        // Write out the snack objects to the library file.
        //Reuses a previously created method to do this
        String snackAsText;
        List<Snack> snackList = this.getAllSnacks();
        for (Snack currentSnack : snackList) {
            // turn a snack into a String
            snackAsText = marshallSnack(currentSnack);
            // write the snack object to the file
            out.println(snackAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up and close printwriter
        out.close();
    }
    
    //just for testing
    public void addSnack(Snack snack, int id){
        vMachine.put(id, snack);
    }
}
