package com.ap.vendingmachinecontroller;

import com.ap.vendingmachine.dao.VendingMachinePersistenceException;
import com.ap.vendingmachine.dto.Change;
import com.ap.vendingmachine.dto.Snack;
import com.ap.vendingmachine.service.InsufficientFundsException;
import com.ap.vendingmachine.service.NoItemInventoryException;
import com.ap.vendingmachine.service.VendingMachineServiceLayer;
import com.ap.vendingmachine.ui.UserIO;
import com.ap.vendingmachine.ui.UserIOConsoleImpl;
import com.ap.vendingmachine.ui.VendingMachineView;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineController {
    
    private VendingMachineServiceLayer service;
    private VendingMachineView view;
    private UserIO io = new UserIOConsoleImpl();
    
    //constructor for the controller
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.service = service;
        this.view = view;
    }
    
    public void run() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException, InsufficientFundsException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            
            menuSelection = getMenuSelection();
            
            switch (menuSelection) {
                case 1:
                    listStock();
                    break;
                case 2:
                    viewSnack();
                    break;
                case 3:
                    addMoney();
                    break;
                case 4:
                    viewBalance();
                    break;
                case 5:
                    purchaseSnack();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    }
    //used to call external method to do menu selection
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    //used to view the current stock of the vending machine
    private void listStock() throws VendingMachinePersistenceException, FileNotFoundException{
        view.displayStockBanner();
        List<Snack> snackList = service.getCurrentStock();
        view.displayStock(snackList);
    }
    // display unknown command to user
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    //display an exit message to user 
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    //display information and stock of a single snack based on user input
    private void viewSnack() throws VendingMachinePersistenceException, FileNotFoundException{
        view.displayItemBanner();
        int id = view.getItemId();
        Snack currentSnack = service.viewItem(id);
        view.viewSnack(currentSnack);
    }
    
    //allows user to add money to the vending machine
    private void addMoney() throws VendingMachinePersistenceException{
        view.displayAddMoneyBanner();
        Double cash = view.addMoney();
        String cashInsert = cash.toString();
        service.addMoney(cashInsert);
    }
    
    //allows user to see current balance
    private void viewBalance(){
        String balance = service.getCurrentBalance();
        view.displayBalance(balance);
        
    }
    
    //allows user to buy a snack
    private void purchaseSnack() throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException, InsufficientFundsException{
        int id = view.getItemId();
        String balance = service.getCurrentBalance();
        BigDecimal userBalance = new BigDecimal(balance);
       // service.validateItemAndMoney(id, userBalance);
       try{ 
                Snack userSnack = service.getItem(id);
                BigDecimal cashLeft = service.purchaseItem(id);
                String leftOverCash = cashLeft.toString();
                Change userChange = service.calculateChange(cashLeft);
                service.updateItem(id);
                view.getSnack(userSnack);
                view.getChange(leftOverCash, userChange);
                view.displayPuchaseSuccessBanner();

       }catch(NoItemInventoryException e){
            io.print(service.viewItem(id).getItemName() + " is not in stock");
        }
       catch(InsufficientFundsException e){
            io.print("Not enough money inserted. \n Current Balance: " + service.getCurrentBalance());
        }
       catch (Exception e){
           io.print("Another Type of Error Has Happened - Please Contact Admin");
       }
    }
}
