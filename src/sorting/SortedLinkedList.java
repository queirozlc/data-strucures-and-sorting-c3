package sorting;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;

public class SortedLinkedList<T extends Comparable<T>> extends LinkedList<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public List<T> quickSort() {
        return quickSort(0, size() - 1);
    }

    private List<T> quickSort(int left, int right) {
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

        return this;
    }

    private void swap(int i, int j) {
        T temp = get(i);
        set(i, get(j));
        set(j, temp);
    }
}
