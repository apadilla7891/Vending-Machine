package com.ap.vendingmachine.service;

import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.dto.Change;
import com.ap.vendingmachine.dto.Snack;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineServiceLayerFileImplTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerFileImplTest(){
        //VendingMachineDao dao = new VendingMachineDaoStubImpl();
        //VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        //service = new VendingMachineServiceLayerFileImpl(dao,auditDao);
        //update service layer test to use spring DI
        
        ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer",VendingMachineServiceLayerFileImpl.class);
    }

    @Test
    public void testGetCurrentStock() throws VendingMachinePersistenceException, FileNotFoundException {
       //Arrange
        Snack test1Clone = new Snack(1);
        test1Clone.setInventory(3);
        test1Clone.setItemCost(new BigDecimal("0.50"));
        test1Clone.setItemName("Test chips");
        Snack test2Clone = new Snack(2);
        test2Clone.setInventory(0);
        test2Clone.setItemCost(new BigDecimal("0.75"));
        test2Clone.setItemName("Test cookies");
        
        //Act and Assert
        assertEquals(1,service.getCurrentStock().size(), "Should only have one snack");
        assertTrue(service.getCurrentStock().contains(test1Clone), "There should only be test chips");
        assertFalse(service.getCurrentStock().contains(test2Clone), "There should not be test cookies");
    }
    @Test
    public void testGetItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //arrange
        Snack test1Clone = new Snack(1);
        test1Clone.setInventory(3);
        test1Clone.setItemCost(new BigDecimal("0.50"));
        test1Clone.setItemName("Test chips");
        
        //Act&Assert
        Snack retrieved = service.getItem(1);
        assertNotNull(retrieved, "getting 1 should not be null");
        assertEquals(test1Clone, retrieved, " Snack stored should be test chips");
    }
    
    @Test
    public void testAddMoney() throws VendingMachinePersistenceException{
        //Arrange
        BigDecimal testMoney = new BigDecimal("1.53");
        //Act and assert
        BigDecimal added = service.addMoney("1.53");
        
        assertEquals(testMoney,added,"They should both be the same big decimal");
    }
    
    @Test
    public void testViewItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //arrange
        Snack test1Clone = new Snack(1);
        test1Clone.setInventory(3);
        test1Clone.setItemCost(new BigDecimal("0.50"));
        test1Clone.setItemName("Test chips");
        
        //Act&Assert
        Snack retrieved = service.viewItem(1);
        assertNotNull(retrieved, "getting 1 should not be null");
        assertEquals(test1Clone, retrieved, " Snack stored should be test chips");
    }
    
    @Test
    public void testUpdateItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //arrange
        int test = 2;
        Snack testItem = service.updateItem(1);
        int result = testItem.getInventory();
        
        //act and assert
        assertEquals(test,result, "They should be the same number after invetory update");
    }
    
    @Test
    public void testPurchaseItem() throws VendingMachinePersistenceException, FileNotFoundException, InsufficientFundsException{
        //arrange
        BigDecimal test = new BigDecimal("1.00");
        BigDecimal added = service.addMoney("1.50");
        
        //Act and assert
        BigDecimal result = service.purchaseItem(1);
        assertEquals(test, result, "They should be the same bigdecimal after the test was charged");
    }
    
    @Test
    public void testCalculateChange() throws VendingMachinePersistenceException{
        //arrange
        Change test = new Change();
        test.setQuarters(new BigDecimal("2"));
        test.setDimes(new BigDecimal("1"));
        test.setNickels(new BigDecimal("1"));
        test.setPennies(new BigDecimal("3"));
        
        //Act and assert
        Change result = service.calculateChange(new BigDecimal("0.68"));
        assertEquals(test.getQuarters(),result.getQuarters(), "They should be 2 quarters");
        assertEquals(test.getDimes(),result.getDimes(), "They should be 1 dime");
        assertEquals(test.getNickels(),result.getNickels(), "They should be 1 nickel");
        assertEquals(test.getPennies(),result.getPennies(), "They should be 3 pennies");
    }
}
