package com.ap.vendingmachine.ui;

import com.ap.vendingmachine.dto.Change;
import com.ap.vendingmachine.dto.Snack;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineView {
    private UserIO io;
    
    public VendingMachineView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection(){
        //choices for user to make when using the program`  
            io.print("Main Menu");
            io.print("1. List Stock");
            io.print("2. View Snack");
            io.print("3. Add Money");
            io.print("4. View Current Balance");
            io.print("5. Purchase a Snack");
            io.print("6. Exit");

            return io.readInt("Please select from the"
                    + " above choices.", 1, 6);
    }
    
    public void displayPuchaseSuccessBanner() {
    io.readString(
            "Item successfully purchased.  Please hit enter to continue");
    }
    
    //banner for exiting program
    public void displayExitBanner() {
        io.print("Thank you for using this program. Good bye!");
    }
    
    //banner for leeting user know stock will be displayed
    public void displayStockBanner() {
        io.print("Current Items in Stock");
    }
    //banner for leeting user know an item will be displayed
    public void displayItemBanner() {
        io.print("Displaying Item");
    }
    //banner for letting user know they will be inserting money will be displayed
    public void displayAddMoneyBanner() {
        io.print("Adding Money");
    }
    //banner for unknown commands
    public void displayUnknownCommandBanner() {
        io.print("Error Unknown Command!");
    }    
    
    //error message
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    //prints out the current stock to user
    public void displayStock(List<Snack> inStock){
        //goes through new list to print out to user current stock
       for(Snack currentSnack: inStock){
           String snackInfo = "Id: " + currentSnack.getId() + " Name: " + currentSnack.getItemName() + " $" + currentSnack.getItemCost().toString();
           io.print(snackInfo);
       }
    }
    
    //prints out the current balance to user
    public void displayBalance(String currentBalance){
        io.print(currentBalance);
    }
    
    //gets the money user is inserting Snack purchasedItem
    public double addMoney(){
        return io.readDouble("Please insert cash: ", 0.00, 100.00);
    }
    
    //get user snack choice
    public int getItemId(){
        return io.readInt("Please enter the id corresponding to the snack", 1,4);
    }
    
    //prints the user snack selected
    public void getSnack(Snack choice){
        io.print(choice.getItemName() + " purchased!");
    }
    
    //get the information for a snack
    public void viewSnack(Snack item){
        io.print(item.getItemName() + " Id: " + item.getId() + " Inventory: " + item.getInventory() + " Cost:$" + item.getItemCost().toString());
    }
    
    public void getChange(String change, Change userChange){
        io.print("Returning your change " + change + " in the form of " + userChange.toString());
    }
}
