package javaTraining.concurrency;

import java.util.*;

public class WaitandNotify {
    String text;
    String[] wordList;

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();

        String text = "Fingertips, each packed with more than a thousand sweat glands, can produce between 100 to 1,000 times more sweat than most other body parts. It might be hard to notice how sweaty they are, though, since the sweat typically evaporates from fingertips as soon as it comes out. This new device collects it before it can. \n" +
                "\n" +
                "The device was built to be highly absorbent. First, sweat is absorbed and converted into energy by a padding of carbon foam electrodes. The electrodes have enzymes that trigger chemical reactions between lactate and oxygen molecules in sweat, generating electricity. There's also a chip underneath the electrodes made of piezoelectric material that generates more energy when pressed.\n" +
                "\n" +
                "Electrical energy is stored in a small capacitor as a wearer sweats or presses on it. It can then be discharged to fuel low-powered devices.  ";
        String[] reads = text.split(" ");
//        List<String> list = Arrays.asList(reads);
//        System.out.println(list);
        String[] writes = new String[reads.length];

        Thread p1 = new Thread(() -> {
            try {
                worker.produce(reads);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "p1");

        Thread c1 = new Thread(() -> {
            try {
                worker.consume(writes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "c1");

        Thread c2 = new Thread(() -> {
            try {
                worker.consume(writes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "c2");

        p1.start();
        c1.start();
        c2.start();

        Thread.sleep(5000L);
        System.out.println(String.join(" ", writes));
    }

}

class Worker {

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
                    queue.add(""+producedIndex+"&&"+input[producedIndex]);
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
