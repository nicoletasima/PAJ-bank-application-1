package com.luxoft.bankapp.email;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Email> queue;

    public Queue() {
        this.queue = new ArrayList<>();
    }

    public void add(Email email) {
        queue.add(email);
    }

    public Email pop() {
        Email em = queue.get(0);
        queue.remove(em);
        return em;
    }

//    public isEmpty() {
//        return queue.isEmpty();
//    }

}
