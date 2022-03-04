package com.ap.vendingmachine.dao;

import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface VendingMachineDao {

    /**
     * Lists all the snacks in stock to the user
     */
    List<Snack> getAllSnacks()throws VendingMachinePersistenceException, FileNotFoundException;
    
    /**
     * Using the given id it returns the corresponding snack if in stock, error message if it is not.
     */
    Snack getSnack(int id) throws VendingMachinePersistenceException, FileNotFoundException;
    
    /**
     * Updates the stock of a snack after a successful purchase
     */
    public void updateSnackStock(int id) throws VendingMachinePersistenceException, FileNotFoundException;
    
    //made just for testing
    public void addSnack(Snack snack, int id);
}   
