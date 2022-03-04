package com.ap.vendingmachine.service;

import com.ap.vendingmachine.dao.VendingMachineDao;
import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    public Snack test1;
    public Snack test2;

    public VendingMachineDaoStubImpl(){
        test1 = new Snack(1);
        test1.setInventory(3);
        test1.setItemCost(new BigDecimal("0.50"));
        test1.setItemName("Test chips");
        test2 = new Snack(2);
        test2.setInventory(0);
        test2.setItemCost(new BigDecimal("0.75"));
        test2.setItemName("Test cookies");
    }
    
    public VendingMachineDaoStubImpl(Snack testSnack){
        this.test1 = testSnack;
    }
    
    @Override
    public List<Snack> getAllSnacks() throws VendingMachinePersistenceException, FileNotFoundException {
        List<Snack> snackList = new ArrayList<>();
        snackList.add(test1);
        snackList.add(test2);
        return snackList;
    }

    @Override
    public Snack getSnack(int id) throws VendingMachinePersistenceException, FileNotFoundException {
        if(id == test1.getId()){
            return test1;
        }else{
            return null;
        }
        
    }

    @Override
    public void updateSnackStock(int id) throws VendingMachinePersistenceException, FileNotFoundException {
        test1.setInventory(test1.getInventory() - 1);
    }
    @Override
    public void addSnack(Snack snack, int id){
    }
}
