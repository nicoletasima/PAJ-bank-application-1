package com.luxoft.bankapp.domain;

import java.util.*;

public class BankReport {

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
       int nrOfAccounts = 0;

       for (Client client: bank.getClients()) {
           nrOfAccounts += client.getAccounts().size();
       }

       return nrOfAccounts;
    }

    public SortedSet getClientsSorted(Bank bank) {
        TreeSet clients = new TreeSet<>(bank.getClients());

        return clients;
    }

    public double getTotalSumInAccounts(Bank bank) {
        // Set clients = bank.getClients();
        double sum = 0.0;
        for (Client client: bank.getClients()) {
            for (Account account: client.getAccounts()) {
                sum += account.getBalance();
            }
        }
        return sum;
    }

    public double getBankCreditSum(Bank bank) {
        double sum = 0.0;

        for (Client client: bank.getClients()) {
            for (Account account: client.getAccounts()) {
                if (account instanceof CheckingAccount) {
                    sum += ((CheckingAccount) account).getOverdraft();
                }
            }
        }

        return sum;
    }

    public SortedSet getAccountsSortedBySum(Bank bank) {
        List<AbstractAccount> accounts = new LinkedList<>();

        for (Client client: bank.getClients()) {
            for (Account account : client.getAccounts()) {
                accounts.add((AbstractAccount) account);
            }
        }

        Collections.sort(accounts);
        SortedSet<AbstractAccount> sortedAccounts = new TreeSet<>(accounts);
        return sortedAccounts;
    }


    public Map <Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        var accountsMap = new HashMap<Client, Collection<Account>>();

        for (Client client: bank.getClients()) {
            accountsMap.put(client, client.getAccounts());
        }

        return accountsMap;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        var clientsMap = new HashMap<String, List<Client>>();

        for (Client client: bank.getClients()) {
            clientsMap.computeIfAbsent(client.getCity(), k-> new ArrayList<>()).add(client);
        }

        return clientsMap;
    }

}
