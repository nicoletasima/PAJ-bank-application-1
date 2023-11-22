package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Test4 {

    private static Bank bank;
    private static BankReportStreams bankReportStreams;

    @BeforeClass
    public static void setup() {
        bank = new Bank();
        bankReportStreams = new BankReportStreams();
        Client client1 = new Client("John", Gender.MALE);
        client1.setCity("Bucharest");
        Client client2 = new Client("Smith", Gender.MALE);
        client2.setCity("London");
        Account account1 = new SavingAccount(1, 100);
        Account account2 = new CheckingAccount(2, 100, 20);
        Account account3 = new SavingAccount(3, 500);
        client1.addAccount(account1);
        client1.addAccount(account2);
        client2.addAccount(account3);

        try {
            BankService.addClient(bank, client1);
            BankService.addClient(bank, client2);
        } catch(ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1.getName());
        }
    }
    @Test
    public void testGetNumberOfClients() {
        assertEquals(2, bankReportStreams.getNumberOfClients(bank));
    }

    @Test
    public void testGetNUmberOfAccounts() {
        assertEquals(3, bankReportStreams.getNumberOfAccounts(bank));
    }

    @Test
    public void testGetClientsSorted() {
        Set clientsSorted = bankReportStreams.getClientsSorted(bank);
        Iterator it = clientsSorted.iterator();
        Client client1 = (Client) it.next();
        assertEquals("John", client1.getName());
        Client client2 = (Client) it.next();
        assertEquals("Smith", client2.getName());
    }

    @Test
    public void testGetTotalSumInAccounts() {
        assertEquals(600.0, bankReportStreams.getTotalSumInAccounts(bank), 1e-15);
    }

    @Test
    public void testGetBankCreditSum() {
        assertEquals(0.0, bankReportStreams.getBankCreditSum(bank), 1e-15);

    }

    @Test
    public void testGetAccountsSortedBySum() {
        Set sortedAccounts = bankReportStreams.getAccountsSortedBySum(bank);
        Iterator it = sortedAccounts.iterator();

        Account account1 = (Account) it.next();
        assertEquals(1, account1.getId());
        assertEquals(100.0, account1.getBalance(), 1e-15);

        Account account2 = (Account) it.next();
        assertEquals(3, account2.getId());
        assertEquals(500.0, account2.getBalance(), 1e-15);
    }

    @Test
    public void testGetCustomerAccounts() {
        Map<Client, Collection<Account>> customerAccounts = bankReportStreams.getCustomerAccounts(bank);
        Iterator<Map.Entry<Client, Collection<Account>>> it = customerAccounts.entrySet().iterator();

        Map.Entry<Client, Collection<Account>> entry1 = it.next();
        assertEquals( "John", entry1.getKey().getName());
        assertEquals(2, (entry1.getKey().getAccounts()).size());

        Map.Entry<Client, Collection<Account>> entry2 = it.next();
        assertEquals("Smith", entry2.getKey().getName());
        assertEquals(1, entry2.getValue().size());
    }

    @Test
    public void testGetClientsByCity() {
        Map<String, List<Client>> clientsByCity = bankReportStreams.getClientsByCity(bank);
        Iterator<Map.Entry<String, List<Client>>> it = clientsByCity.entrySet().iterator();

        Map.Entry<String, List<Client>> entry1 = it.next();
        assertEquals("Bucharest", entry1.getKey());
        assertEquals(1, entry1.getValue().size());
        assertEquals("John", entry1.getValue().get(0).getName());

        Map.Entry<String, List<Client>> entry2 = it.next();
        assertEquals("London", entry2.getKey());
        assertEquals(1, entry2.getValue().size());
        assertEquals("Smith", entry2.getValue().get(0).getName());
    }
}
