package javaTraining.concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedWorker {

    private final int capacity = 20;
    int producedUnit = 0;
    // to track how many words have been produced
    volatile int producedIndex = 0;
    // the middle man to handle the data in transit
//    Map<Integer, String> warehouse = new HashMap<>(capacity);
    volatile Queue<String> queue = new LinkedList<String>();

    public synchronized void produce(String[] input) throws InterruptedException {

        boolean running = true;
        while (running) {
            System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
            Thread.sleep(10);
//            if (producedUnit >= capacity || producedIndex >= capacity) {
            if (producedUnit >= capacity) {
                System.out.println("The warehouse is overloaded. Stop producing");
                wait();
            } else {
                /**
                 * What's the proper input data structure?
                 * Should the already-read words be removed from the input?
                 *      If removed, how to keep the index correct?
                 * If the input is a HashMap, any issue?
                 *
                 *
                 * Method 2:
                 * add a synchronized var to track the used index?
                 */
//                warehouse.put(producedIndex, input[producedIndex]);
//                synchronized (this) {
                queue.add("" + producedIndex + "&&" + input[producedIndex]);
                producedIndex++;
                producedUnit++;
//                }
            }

            if (producedIndex == input.length) {
                running = false;
            }
//            if (producedUnit < capacity && producedIndex < capacity) {
            if (producedUnit < capacity) {
                notifyAll();
            }
        }
    }

    public synchronized void consume(String[] output) throws InterruptedException {

        boolean consuming = true;
        while (consuming) {
            System.out.println(Thread.currentThread().getName() + "::: run :::" + producedUnit);
            Thread.sleep(10);
            if (producedUnit <= 0) {
                wait();
            } else {
//                synchronized (this) {
                String element = queue.poll();
                String[] keyVal = element.split("&&");
                output[Integer.parseInt(keyVal[0])] = keyVal[1];
                producedUnit--;
//                }
            }

            if (producedIndex == output.length && producedUnit == 0) {
                consuming = false;
            }
            notifyAll();

        }
    }
}
