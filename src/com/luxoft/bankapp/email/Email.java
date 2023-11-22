package com.luxoft.bankapp.email;

import com.luxoft.bankapp.domain.Client;

public class Email {
    private Client client;
    String from;
    String to;

    public Email(Client client, String from, String to) {
        this.client = client;
        this.from = from;
        this.to = to;
    }


}
