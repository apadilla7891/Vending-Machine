package com.ap.vendingmachine.dto;

/**
 *
 * @author Andy Padilla
 */
public enum CoinValue {
    QUARTER("0.25"), DIME("0.10"),NICKEL("0.05"),PENNY("0.01");
    
    private String value;
    CoinValue(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}
