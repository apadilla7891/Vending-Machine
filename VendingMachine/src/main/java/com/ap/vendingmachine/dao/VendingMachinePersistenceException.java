package com.ap.vendingmachine.dao;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachinePersistenceException extends Exception{
    //exception case when something is wrong but it isn't cased by another exception ie one of the fields of a snackisn't valid
    public VendingMachinePersistenceException(String message) {
        super(message);
    }
    
    //exception when something is wrong and is caused by another exception ie implementation-specific exception
    public VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
