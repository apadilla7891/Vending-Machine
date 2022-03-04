package com.ap.vendingmachine.service;

import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.dto.Change;
import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface VendingMachineServiceLayer {
    
    BigDecimal addMoney(String cashInsert)throws VendingMachinePersistenceException;
    
    String getCurrentBalance();
    
    Snack getItem(int id) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException;
    
    Snack viewItem(int id) throws VendingMachinePersistenceException, FileNotFoundException;
    
    BigDecimal purchaseItem(int id)throws VendingMachinePersistenceException, FileNotFoundException, InsufficientFundsException;
    
    public List<Snack> getCurrentStock()throws VendingMachinePersistenceException, FileNotFoundException;
    
    Change calculateChange(BigDecimal userChange)throws VendingMachinePersistenceException;
    
    Snack updateItem(int id) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException;
}
