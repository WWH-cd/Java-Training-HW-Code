package javaTraining.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SemaphoreWorker {

    private final int capacity = 20;
    int producedUnit = 0;
    // to track how many words have been produced
    volatile int producedIndex = 0;
    // the middle man to handle the data in transit
//    Map<Integer, String> warehouse = new HashMap<>(capacity);
    volatile Queue<String> queue = new LinkedList<String>();
    private Semaphore semaphore = new Semaphore(1);

    public void produce(String[] input) throws InterruptedException {

        boolean running = true;
        while (running) {
//            semaphore.acquire();

            if (semaphore.tryAcquire(1, SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
                    Thread.sleep(10);
                    if (producedUnit >= capacity) {
                        System.out.println("The warehouse is overloaded. Stop producing");
                        semaphore.release();
                    } else {
                        queue.add("" + producedIndex + "&&" + input[producedIndex]);
                        producedIndex++;
                        producedUnit++;
                    }

                    if (producedIndex == input.length) {
                        running = false;
                    }
                } finally {
                    semaphore.release();
                }
            }
        }
    }

    public void consume(String[] output) throws InterruptedException {

        boolean consuming = true;
        while (consuming) {
//            semaphore.acquire();
            if (semaphore.tryAcquire(1, SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
                    Thread.sleep(10);
                    if (producedUnit <= 0) {
                        System.out.println("No unit to be consumed. Stop consuming.");
                        semaphore.release();
                        continue;
                    } else {
                        String element = queue.poll();
                        if (element == null) {
                            continue;
                        }
                        String[] keyVal = element.split("&&");
                        output[Integer.parseInt(keyVal[0])] = keyVal[1];
                        producedUnit--;
                    }

                    if (producedIndex == output.length && producedUnit == 0) {
                        consuming = false;
                    }
                } finally {
                    semaphore.release();
                }
            }
        }
    }
}
