package com.ap.vendingmachine.service;

/**
 *
 * @author Andy Padila
 */
public class InsufficientFundsException extends Exception{
    //exception case when something is wrong but it isn't cased by another exception ie one of the fields of a snackisn't valid
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    //exception when something is wrong and is caused by another exception ie implementation-specific exception
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
