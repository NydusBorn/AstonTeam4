package sortapp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadFeatures {

    public static <T> int countOccurrencesMultithreaded(List<T> collection, T target, int numberOfThreads) {
        if (collection == null || collection.isEmpty()) return 0;
        if (numberOfThreads <= 0) numberOfThreads = 1;
        if (numberOfThreads > collection.size()) numberOfThreads = collection.size();

        AtomicInteger totalCount = new AtomicInteger(0);
        Thread[] threads = new Thread[numberOfThreads];
        int partSize = collection.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int start = i * partSize;
            final int end = (i == numberOfThreads - 1) ? collection.size() : start + partSize;
            threads[i] = new Thread(() -> {
                int localCount = 0;
                for (int j = start; j < end; j++) {
                    if (collection.get(j).equals(target)) localCount++;
                }
                totalCount.addAndGet(localCount);
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) {}
        }
        return totalCount.get();
    }

    public static <T extends Comparable<T>> int binarySearch(List<T> sortedList, T target) {
        int left = 0, right = sortedList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = sortedList.get(mid).compareTo(target);
            if (cmp == 0) return mid;
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}