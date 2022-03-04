package com.ap.vendingmachine;

import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.service.InsufficientFundsException;
import com.ap.vendingmachine.service.NoItemInventoryException;
import com.ap.vendingmachinecontroller.VendingMachineController;
import java.io.FileNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Andy Padilla
 */
public class App {
    
    public static void main(String[] args) throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException, InsufficientFundsException {
        //UserIO myIo = new UserIOConsoleImpl();
        //VendingMachineView myView = new VendingMachineView(myIo);
        //VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        //VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        //VendingMachineServiceLayer myService = new VendingMachineServiceLayerFileImpl(myDao, myAuditDao);
        //VendingMachineController controller = new VendingMachineController(myService, myView);
        //controller.run();
        
        //updated code to use Spring DI
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller",VendingMachineController.class);
        controller.run();
    }
}
