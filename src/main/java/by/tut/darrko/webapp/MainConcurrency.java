package by.tut.darrko.webapp;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    // private static final Object LOCK = new Object();
    private static final Lock LOCK = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat());
    final Account account1 = new Account();
    final Account account2 = new Account();
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                // throw new IllegalStateException();
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
        CountDownLatch countDownLatch = new CountDownLatch(THREADS_NUMBER);

        ExecutorService executorService = Executors.newCachedThreadPool();
//
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    // System.out.println(threadLocal.get().format(new Date()));
                }
                countDownLatch.countDown();
            });

            /* Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                countDownLatch.countDown();
            });
            thread.start();
            // threads.add(thread);
            */


        }

        /* threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/

        countDownLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        // System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());

        Thread thread3 = new Thread(() -> {
            try {
                mainConcurrency.account1.addMoney(mainConcurrency.account2, 200);
                System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account1);
                System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                mainConcurrency.account2.addMoney(mainConcurrency.account1, 300);
                System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account1);
                System.out.println(Thread.currentThread().getName() + ", " + mainConcurrency.account2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // thread3.start();
        // thread4.start();

        // System.out.println(mainConcurrency.account1);
        // System.out.println(mainConcurrency.account2);

    }


    private void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {


        atomicCounter.incrementAndGet();

    /*    LOCK.lock();
        try {
            counter++;
        }
        finally {
            LOCK.unlock();
        } */
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
            anotherAccount.setAmount(-amount);
            setAmount(amount);
        }

        @Override
        public String toString() {
            return "Account{" +
                    "amount=" + amount +
                    '}';
        }
    }

}

