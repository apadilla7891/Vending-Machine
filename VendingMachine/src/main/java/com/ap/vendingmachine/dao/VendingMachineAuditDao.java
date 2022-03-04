package com.ap.vendingmachine.dao;

/**
 *
 * @author Andy Padilla
 */
public interface VendingMachineAuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
