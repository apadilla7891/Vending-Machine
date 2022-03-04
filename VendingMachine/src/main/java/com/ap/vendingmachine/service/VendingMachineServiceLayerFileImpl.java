package com.ap.vendingmachine.service;

import com.ap.vendingmachine.dao.VendingMachineAuditDao;
import com.ap.vendingmachine.dao.VendingMachineDao;
import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.dto.Change;
import static com.ap.vendingmachine.dto.CoinValue.DIME;
import static com.ap.vendingmachine.dto.CoinValue.NICKEL;
import static com.ap.vendingmachine.dto.CoinValue.PENNY;
import static com.ap.vendingmachine.dto.CoinValue.QUARTER;
import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineServiceLayerFileImpl implements VendingMachineServiceLayer{
    
    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;
    BigDecimal currentBalance = new BigDecimal("0.00");
    private Change userReturn = new Change();
    
    //Constructor to make service layer that the controller needs to go through to get to the dao and audit dao/ vice versa
    public VendingMachineServiceLayerFileImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    
    /**
     * Adds the user inserted money into the vending machine's current balance for use to purchase snacks
     * @param cashInsert
     * @return 
     */
    @Override
    public BigDecimal addMoney(String cashInsert) throws VendingMachinePersistenceException{
        BigDecimal inserted = new BigDecimal(cashInsert);
        inserted.setScale(2, RoundingMode.HALF_UP);
        currentBalance = currentBalance.add(inserted);
        auditDao.writeAuditEntry(inserted.toString() + " inserted.");
        return currentBalance;
    }
    
    /**
     * Assuming that the Snack is confirmed to be in stock by the validate class then this class is called to return snack to user.
     * The stock is also updated to reflect the sale and the transaction is logged in the audit file
     * @param id
     * @return 
     */
    @Override
    public Snack getItem(int id) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException{
        Snack item = dao.getSnack(id);
        if(item.getInventory() <= 0){
            throw new NoItemInventoryException(item.getItemName() + " is not in stock");
        }
        return item;
    }
    
    @Override
    public Snack updateItem(int id) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException{
        Snack item = dao.getSnack(id);
        dao.updateSnackStock(id);
        String itemName = item.getItemName();
        auditDao.writeAuditEntry(itemName  + " sold");
        return item;
    }
    //same as get item but used to view info so no offical purchase
    @Override
    public Snack viewItem(int id) throws VendingMachinePersistenceException, FileNotFoundException{
        Snack item = dao.getSnack(id);
        return item;
    }
    
    /**
     * Assuming that the current balance passes the check and the item is in stock then it calculates the difference between the current balance and item cost, 
     * and returns it so another class may calculate change. Also zeroes the balance so user needs to inser more money if they wish to make another transaction.
     * @param id
     * @return
     * @throws VendingMachinePersistenceException
     * @throws FileNotFoundException 
     */
    @Override
    public BigDecimal purchaseItem(int id) throws VendingMachinePersistenceException, FileNotFoundException,InsufficientFundsException{
        Snack item = dao.getSnack(id);
        BigDecimal cost = item.getItemCost();
        if(currentBalance.compareTo(item.getItemCost()) == -1){
            throw new InsufficientFundsException("No enough money inserted. \n Current Balance: " + currentBalance.toString());
        }
        BigDecimal difference = currentBalance.subtract(cost);
        currentBalance = new BigDecimal("0.00");
        return difference;
    }
    
    /**
     * Prints out the current items in stock using lambda streams
     */
    @Override
    public List<Snack> getCurrentStock() throws VendingMachinePersistenceException, FileNotFoundException{
        List<Snack> inventory = dao.getAllSnacks();
        //filters the items with at least 1 inventory to new list
        List<Snack> inStock = inventory.stream().filter((p) -> p.getInventory() > 0).collect(Collectors.toList());
        //return the list to be printed out to user using the view
        return inStock;
        
        //old stuff below
        //goes through new list to print out to user current stock
        //inStock.stream().forEach((p) -> System.out.println("Id: "+ p.getId() + " Name: "+ p.getItemName() + " Stock: " + p.getInventory() + " Cost: $" + p.getItemCost().toString()));
    }
    
    /**
     * Takes the cash after the transaction and splits it into coins in order to return the user's change. In the controller it is printed to the console.
     * @param userChange
     * @return 
     */
    @Override
    public Change calculateChange(BigDecimal userChange)throws VendingMachinePersistenceException{
        BigDecimal numOfQuarters = userChange.divideToIntegralValue(new BigDecimal(QUARTER.getValue()));
        userReturn.setQuarters(numOfQuarters);
        userChange = userChange.subtract(numOfQuarters.multiply(new BigDecimal(QUARTER.getValue())));
        BigDecimal numOfDimes = userChange.divideToIntegralValue(new BigDecimal(DIME.getValue()));
        userReturn.setDimes(numOfDimes);
        userChange = userChange.subtract(numOfDimes.multiply(new BigDecimal(DIME.getValue())));
        BigDecimal numOfNickels = userChange.divideToIntegralValue(new BigDecimal(NICKEL.getValue()));
        userReturn.setNickels(numOfNickels);
        userChange = userChange.subtract(numOfNickels.multiply(new BigDecimal(NICKEL.getValue())));
        BigDecimal numOfPennies = userChange.divideToIntegralValue(new BigDecimal(PENNY.getValue()));
        userReturn.setPennies(numOfPennies);
        userChange = userChange.subtract(numOfPennies.multiply(new BigDecimal(PENNY.getValue())));
        return userReturn;
    }
    
    //Informs user of the  current balance available in the form of a string
    @Override
    public String getCurrentBalance(){
        return currentBalance.toString();
    }
}
