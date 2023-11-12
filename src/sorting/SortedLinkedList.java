package sorting;

import java.io.Serial;
import java.util.LinkedList;

public class SortedLinkedList<T extends Comparable<T>> extends LinkedList<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public void quickSort() {
        this.quickSort(0, size() - 1);
    }

    public void shellSort() {
        int h = 1;
        int n = size();

        while (h < n) h = h * 3 + 1;

        do {
            h = h / 3;
            for (var i = h; i < n; i++) {
                T value = get(i);
                int j = i - h;

                while (j >= 0 && value.compareTo(get(j)) < 0) {
                    set(j + h, get(j));
                    j = j - h;
                }

                set(j + h, value);
            }

        } while (h != 1);

    }

    private void quickSort(int left, int right) {
        int pivot = (left + right) / 2;
        int i = left;
        int j = right;

        while (i <= j) {
            while (get(i).compareTo(get(pivot)) < 0) i++;
            while (get(j).compareTo(get(pivot)) > 0) j--;

            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }

        if (left < j) quickSort(left, j);
        if (i < right) quickSort(i, right);
    }

    private void swap(int i, int j) {
        T temp = get(i);
        set(i, get(j));
        set(j, temp);
    }
}
