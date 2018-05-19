package by.tut.darrko.webapp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();

    final Account account1 = new Account();
    final Account account2 = new Account();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainConcurrency.account1.addMoney(mainConcurrency.account2, 200);
                    System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account1);
                    System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainConcurrency.account2.addMoney(mainConcurrency.account1, 300);
                    System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account1);
                    System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread3.start();
        thread4.start();

        System.out.println(mainConcurrency.account1);
        System.out.println(mainConcurrency.account2);

    }


    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
        counter++;
//                wait();
//                readFile
//                ...
//        }
    }

    class Account {
        private int amount = 500;

        public int getAmount() {
            return amount;
        }

        public synchronized void setAmount(int amount) throws InterruptedException {

            sleep(500);
            this.amount += amount;

        }

        public synchronized void addMoney(Account anotherAccount, int amount) throws InterruptedException {

            sleep(500);
            setAmount(amount);
            anotherAccount.setAmount(-amount);

        }

        @Override
        public String toString() {
            return "Account{" +
                    "amount=" + amount +
                    '}';
        }
    }

}

