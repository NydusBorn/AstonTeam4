package sortapp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadFeatures {

    public static <T> int countOccurrences(List<T> collection, T target, int numThreads) {
        if (collection == null || collection.isEmpty() || target == null) return 0;
        if (numThreads <= 0) numThreads = 1;
        if (numThreads > collection.size()) numThreads = collection.size();

        AtomicInteger total = new AtomicInteger(0);
        Thread[] threads = new Thread[numThreads];
        int partSize = collection.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * partSize;
            final int end = (i == numThreads - 1) ? collection.size() : start + partSize;
            threads[i] = new Thread(() -> {
                int local = 0;
                for (int j = start; j < end; j++) {
                    if (collection.get(j).equals(target)) local++;
                }
                total.addAndGet(local);
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { }
        }
        return total.get();
    }
}