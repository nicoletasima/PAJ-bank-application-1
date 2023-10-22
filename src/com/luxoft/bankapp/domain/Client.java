package com.luxoft.bankapp.domain;

import java.util.*;

public class Client implements Comparable<Client> {
	
	private String name;
	private Gender gender;
	private Set<Account> accounts = new LinkedHashSet<>();

	private String city;

	public Client() {}

	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	@Override
	public String toString() {
		return getClientGreeting();
	}

	@Override
	public int compareTo(Client o) {
		return name.compareTo(o.getName());
	}
}
