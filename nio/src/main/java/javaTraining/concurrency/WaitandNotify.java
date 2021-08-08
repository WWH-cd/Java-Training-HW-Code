package javaTraining.concurrency;

public class WaitandNotify {
    String text;
    String[] wordList;

    public static void main(String[] args) throws InterruptedException {
        SynchronizedWorker worker = new SynchronizedWorker();

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

