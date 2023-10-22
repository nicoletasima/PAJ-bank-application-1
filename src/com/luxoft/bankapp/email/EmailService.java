package com.luxoft.bankapp.email;

public class EmailService extends Thread {

    Queue q;
    Thread t;
    Object monitor = new Object();


    public EmailService() {
        this.q = new Queue();
        t = new Thread();
        t.start();

    }

    public void sendNotificationEmail(Email email) {
            q.add(email);
    }

    @Override
    public void run() {
        Email em = q.pop();
    }


    public void close() {

    }


}
