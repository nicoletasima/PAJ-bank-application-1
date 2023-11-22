package com.luxoft.bankapp.email;

public class EmailService implements Runnable {

    Queue q;
    Thread t;
    private boolean closed;

    public EmailService() {
        this.q = new Queue();
        t = new Thread();
        t.start();

    }

    public void sendNotificationEmail(Email email) throws Exception {
        if (!closed) {
            q.add(email);
            synchronized (q) {
                q.notify();
            }
        } else {
            throw new Exception("Cannot send emails anymore!");
        }
    }

    @Override
    public void run() {
        Email em;

        while (true) {
            if (closed) {
                return;
            }

            if ((em = q.pop()) != null) {
                sendEmail(em);
            }
            try {
                synchronized(q) {
                    q.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    private void sendEmail(Email email) {
        System.out.println(email);

    }
    public void close() {
        closed = true;
        synchronized (q) {
            q.notify();
        }
    }


}
