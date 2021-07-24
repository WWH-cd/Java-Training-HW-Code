package javaTraining.concurrency;

import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        String[] output = new String[0];
        try {
            output = executorService.submit(new Callable<String[]>() {
                @Override
                public String[] call() throws Exception {
                    ProduceConsume produceConsume = new ProduceConsume();
                    return produceConsume.run();
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(String.join(" ", output));
        executorService.shutdown();
    }
}
