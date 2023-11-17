import sorting.SortedLinkedList;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class QuickSortMultiThreading<T extends Comparable<T>>
        extends RecursiveTask<T> {

    private static final Random RANDOM = new Random();
    private final int start;
    private final int end;
    private final transient T[] arr;

    // Function to implement
    // QuickSort method
    public QuickSortMultiThreading(int start,
                                   int end,
                                   T[] arr) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    public static <T extends Comparable<T>> void sort(SortedLinkedList<T> list) {
        var array = list.toArray();
        var task = new QuickSortMultiThreading<>(0, array.length - 1, array);
        task.compute();
        list.clear();
        for (T t : array) {
            list.add(t);
        }
    }

    /**
     * Finding a random pivoted and partition
     * array on a pivot.
     * There are many different
     * partitioning algorithms.
     *
     * @param start start index
     * @param end   end index
     * @param arr   array to be sorted
     * @return partition index
     */
    private int partition(int start, int end,
                          T[] arr) {
        int i = start;
        int j = end;

        // Decide to choose a pivot
        int pivoted = RANDOM
                .nextInt(j - i)
                + i;


        // Swap the pivoted with an end element of an array

        T t = arr[j];
        arr[j] = arr[pivoted];
        arr[pivoted] = t;
        j--;

        // Start partitioning
        while (i <= j) {

            if (arr[i].compareTo(arr[end]) <= 0) {
                i++;
                continue;
            }

            if (arr[j].compareTo(arr[end]) >= 0) {
                j--;
                continue;
            }

            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }

        // Swap pivoted to its
        // correct position
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }

    @Override
    protected T compute() {
        // Base case
        if (start >= end)
            return null;

        // Find partition
        int p = partition(start, end, arr);

        // Divide array
        QuickSortMultiThreading<T> left
                = new QuickSortMultiThreading<>(start,
                p - 1,
                arr);

        QuickSortMultiThreading<T> right
                = new QuickSortMultiThreading<>(p + 1,
                end,
                arr);

        // Left subproblem as separate thread
        left.fork();
        right.compute();

        // Wait until left thread complete
        left.join();

        // We don't want anything as return
        return null;
    }
}
