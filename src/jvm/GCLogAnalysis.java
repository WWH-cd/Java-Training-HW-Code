package jvm;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
/*
DEMO: GC log generation and analysis
*/
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
        // STARTTIME: current time in millisecond
        long startMillis = System.currentTimeMillis();
        // Configurable execution duration
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // ENDTIME
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("Executing...");
        // Cache some objects; enter the Old Gen of GC
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        // iterate within the time range
        while (System.currentTimeMillis() < endMillis) {
            // generate garbage
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("Execution Finished! " +
                "The total number of times that garbage has been generated: " + counter.longValue());
    }

    // generate objects
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}