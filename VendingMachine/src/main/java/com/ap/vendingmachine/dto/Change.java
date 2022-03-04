package com.ap.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Andy Padilla
 */
public class Change {
    BigDecimal Quarters;
    BigDecimal Dimes;
    BigDecimal Nickels;
    BigDecimal Pennies;

    public BigDecimal getQuarters() {
        return Quarters;
    }

    public void setQuarters(BigDecimal Quarters) {
        this.Quarters = Quarters;
    }

    public BigDecimal getDimes() {
        return Dimes;
    }

    public void setDimes(BigDecimal Dimes) {
        this.Dimes = Dimes;
    }

    public BigDecimal getNickels() {
        return Nickels;
    }

    public void setNickels(BigDecimal Nickels) {
        this.Nickels = Nickels;
    }

    public BigDecimal getPennies() {
        return Pennies;
    }

    public void setPennies(BigDecimal Pennies) {
        this.Pennies = Pennies;
    }
    
    public String toString(){
        return Quarters.toString() + " quarters, " + Dimes.toString() + " dimes, " + Nickels.toString() + " nickels, " + Pennies.toString() + " pennies.";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.Quarters);
        hash = 67 * hash + Objects.hashCode(this.Dimes);
        hash = 67 * hash + Objects.hashCode(this.Nickels);
        hash = 67 * hash + Objects.hashCode(this.Pennies);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Change other = (Change) obj;
        if (!Objects.equals(this.Quarters, other.Quarters)) {
            return false;
        }
        if (!Objects.equals(this.Dimes, other.Dimes)) {
            return false;
        }
        if (!Objects.equals(this.Nickels, other.Nickels)) {
            return false;
        }
        if (!Objects.equals(this.Pennies, other.Pennies)) {
            return false;
        }
        return true;
    }
    
    
}
