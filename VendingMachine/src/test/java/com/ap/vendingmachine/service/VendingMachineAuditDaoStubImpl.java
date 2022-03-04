package com.ap.vendingmachine.service;

import com.ap.vendingmachine.dao.VendingMachineAuditDao;
import com.ap.vendingmachine.dao.VendingMachinePersistenceException;

/**
 *
 * @author Andy Padilla
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //do nothing
    }
    
}
