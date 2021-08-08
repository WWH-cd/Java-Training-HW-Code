package javaTraining.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockWorker {

    private final int capacity = 20;
    int producedUnit = 0;
    // to track how many words have been produced
    volatile int producedIndex = 0;
    // the middle man to handle the data in transit
//    Map<Integer, String> warehouse = new HashMap<>(capacity);
    volatile Queue<String> queue = new LinkedList<String>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void produce(String[] input) throws InterruptedException {

        boolean running = true;
        while (running) {
            boolean isLockAcquired = lock.writeLock().tryLock();
            if (isLockAcquired) {
                try {
                    System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
                    Thread.sleep(10);
                    if (producedUnit >= capacity) {
                        System.out.println("The warehouse is overloaded. Stop producing");
                        lock.writeLock().unlock();
                    } else {
                        queue.add("" + producedIndex + "&&" + input[producedIndex]);
                        producedIndex++;
                        producedUnit++;
                    }

                    if (producedIndex == input.length) {
                        running = false;
                    }
                } finally {
                    if (lock.isWriteLockedByCurrentThread()) {
                        lock.writeLock().unlock();
                    }
                }
            }
        }
    }

    public void consume(String[] output) throws InterruptedException {

        boolean consuming = true;
        while (consuming) {
            boolean isLockAcquired = lock.readLock().tryLock();
            if (isLockAcquired) {
                try {
                    System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
                    Thread.sleep(10);
                    if (producedUnit <= 0) {
                        lock.readLock().unlock();
                    } else {
                        String element = queue.poll();
                        String[] keyVal = element.split("&&");
                        output[Integer.parseInt(keyVal[0])] = keyVal[1];
                        producedUnit--;
                    }

                    if (producedIndex == output.length && producedUnit == 0) {
                        consuming = false;
                    }
                } finally {
                    if (lock.getReadHoldCount() != 0) {
                        lock.readLock().unlock();
                    }
                }
            }
        }
    }
}
