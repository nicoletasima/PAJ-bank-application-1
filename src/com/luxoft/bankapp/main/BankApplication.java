package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BankApplication {
	
	private static Bank bank;
	private static BankReport bankReport;

	private static BankReportStreams bankReportStreams;
	
	public static void main(String[] args) throws IOException {
		bank = new Bank();
		bankReport = new BankReport();
		bankReportStreams = new BankReportStreams();
		modifyBank();
		System.out.println(args[0]);
		System.out.println(args[0]);
		if (args[0].equals("-statistics")) {
			readCommand();
		}

		printBalance();
		BankService.printMaximumAmountToWithdraw(bank);

	}
	
	private static void modifyBank() {
		Client client1 = new Client("John", Gender.MALE);
		client1.setCity("Bucharest");
		Client client2 = new Client("Sergiu", Gender.MALE);
		client2.setCity("Chisinau");
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

		account1.deposit(100);
		try {
		  account1.withdraw(10);
		} catch (OverdraftLimitExceededException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(90);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(100);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  BankService.addClient(bank, client1);
		  BankService.addClient(bank, client2);
		} catch(ClientExistsException e) {
		  System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 
	}
	
	private static void printBalance() {
		System.out.format("%nPrint balance for all clients%n");
		for(Client client : bank.getClients()) {
			System.out.println("Client: " + client);
			for (Account account : client.getAccounts()) {
				System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
			}
		}
	}

	private static void readCommand() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String name = reader.readLine();

		if (name.equals("display statistic")) {
			displayStatistics();
		}
	}

	private static void displayStatistics() {
		System.out.format("Number of bank clients: %d\n", bankReport.getNumberOfClients(bank));

		System.out.format("Total number of accounts for all bank clients: %d\n", bankReport.getNumberOfAccounts(bank));

		System.out.format("Bank clients in alphabetical order: %s\n", bankReport.getClientsSorted(bank).toString());

		System.out.format("Total balance cum for all clients: %.2f\n", bankReport.getTotalSumInAccounts(bank));

		System.out.format("Bank accounts ordered by sum: %s\n", bankReport.getAccountsSortedBySum(bank).toString());

		System.out.format("Total bank amount of credit granted: %.2f\n", bankReport.getBankCreditSum(bank));

		System.out.format("Clients with their accounts: %s\n", bankReport.getCustomerAccounts(bank));

		System.out.format("Clients by city: %s\n", bankReport.getClientsByCity(bank));

	}

	private static void displayStatisticsStreams() {
		System.out.format("Number of bank clients: %d\n", bankReportStreams.getNumberOfClients(bank));

		System.out.format("Total number of accounts for all bank clients: %d\n", bankReportStreams.getNumberOfAccounts(bank));

		System.out.format("Bank clients in alphabetical order: %s\n", bankReportStreams.getClientsSorted(bank).toString());

		System.out.format("Total balance cum for all clients: %.2f\n", bankReportStreams.getTotalSumInAccounts(bank));

		System.out.format("Bank accounts ordered by sum: %s\n", bankReportStreams.getAccountsSortedBySum(bank).toString());

		System.out.format("Total bank amount of credit granted: %.2f\n", bankReportStreams.getBankCreditSum(bank));

		System.out.format("Clients with their accounts: %s\n", bankReportStreams.getCustomerAccounts(bank));

		System.out.format("Clients by city: %s\n", bankReportStreams.getClientsByCity(bank));

	}
}
