package com.ap.vendingmachine.dao;

import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao;
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "testInventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }


    @Test
    public void testGetSnack() throws VendingMachinePersistenceException, FileNotFoundException {
        //arrange
        Snack test = new Snack(1);
        test.setInventory(3);
        test.setItemCost(new BigDecimal("0.50"));
        test.setItemName("test chips");
        
        testDao.addSnack(test, 1);
        //Act and assert
        Snack retrieved = testDao.getSnack(1);
        assertEquals(test.getId(), retrieved.getId(), "Should be 1");
        assertEquals(test.getInventory(), retrieved.getInventory(), "Should be 3");
        assertEquals(test.getItemCost(), retrieved.getItemCost(), "Should be 0.50");
        assertEquals(test.getItemName(), retrieved.getItemName(), "Should be test chips");
        
    }
    
    @Test 
    public void testGetAllSnacks() throws VendingMachinePersistenceException, FileNotFoundException{
        //arrange
        Snack test1 = new Snack(1);
        test1.setInventory(3);
        test1.setItemCost(new BigDecimal("0.50"));
        test1.setItemName("test chips");
        
        Snack test2 = new Snack(2);
        test2.setInventory(2);
        test2.setItemCost(new BigDecimal("1.50"));
        test2.setItemName("test cookies");
        
        testDao.addSnack(test1, 1);
        testDao.addSnack(test2, 2);
        
        //Act and assert
        List<Snack> allSnacks = testDao.getAllSnacks();
        
        assertNotNull(allSnacks,"This list should not be empty");
        assertEquals(2,allSnacks.size(),"Should contain 2 snacks");
        
        assertTrue(testDao.getAllSnacks().contains(test1), "should contain first test snack");
        assertTrue(testDao.getAllSnacks().contains(test2), "should contain second test snack");
    }
    
    @Test
    public void testUpdateSnackStock() throws VendingMachinePersistenceException, FileNotFoundException{
        //Arrange
        Snack test = new Snack(1);
        test.setInventory(3);
        test.setItemCost(new BigDecimal("0.50"));
        test.setItemName("test chips");
        
        testDao.addSnack(test, 1);
        
        //Act and assert
        testDao.updateSnackStock(1);
        
        assertEquals(2, testDao.getSnack(1).getInventory(), "should be 2 after being updated");
    }
}
