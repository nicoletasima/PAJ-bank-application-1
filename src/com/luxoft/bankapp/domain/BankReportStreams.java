package com.luxoft.bankapp.domain;

import java.util.*;
import java.util.stream.Collectors;

public class BankReportStreams {

    public int getNumberOfClients(Bank bank) {
        return (int) bank.getClients().stream().count();
    }

    public int getNumberOfAccounts(Bank bank) {
        return (int) bank.getClients().stream()
                .mapToLong(a -> a.getAccounts().size())
                .sum();
    }

    public SortedSet getClientsSorted(Bank bank) {
        return bank.getClients().stream()
                .sorted()
                .collect(Collectors.toCollection(TreeSet::new));
    }
    public double getTotalSumInAccounts(Bank bank) {
        TreeSet<Account> collect = bank.getClients().stream()
                .flatMap(a -> a.getAccounts().stream())
                .collect(Collectors.toCollection(TreeSet::new));
        return collect.stream().mapToDouble(Account::getBalance).sum();
    }

    public double getBankCreditSum(Bank bank) {
        TreeSet<Account> collect = bank.getClients().stream()
                .flatMap(a -> a.getAccounts().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        return collect.stream().filter(e -> e instanceof CheckingAccount)
                .mapToDouble( e -> ((CheckingAccount) e).getOverdraft())
                .sum();
    }

    public SortedSet getAccountsSortedBySum(Bank bank) {
        TreeSet<Account> collect = bank.getClients().stream()
                .flatMap(a -> a.getAccounts().stream())
                .collect(Collectors.toCollection(TreeSet::new));
        return collect.stream()
                .sorted(Comparator.comparing(o-> o.getBalance()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {

        return bank.getClients()
                .stream()
                .collect(Collectors.toMap(entry -> entry, Client::getAccounts));
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {

        var clientsMap = new HashMap<String, List<Client>>();
        bank.getClients()
                .forEach(client -> clientsMap.computeIfAbsent(client.getCity(), k-> new ArrayList<>()).add(client));

        return clientsMap;
    }


}
