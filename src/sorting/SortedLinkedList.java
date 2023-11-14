package sorting;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SortedLinkedList<T extends Comparable<T>> {

    private Node<T> head;
    private int size;
    private Node<T> tail;

    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public static <E extends Comparable<E>> SortedLinkedList<E> fromArray(E[] array) {
        if (Objects.isNull(array)) throw new IllegalArgumentException("Array cannot be null");
        var list = new SortedLinkedList<E>();
        list.addAll(Arrays.asList(array));
        return list;
    }

    public static <E extends Comparable<E>> SortedLinkedList<E> fromList(List<E> list) {
        if (Objects.isNull(list)) throw new IllegalArgumentException("List cannot be null");
        var linkedList = new SortedLinkedList<E>();
        linkedList.addAll(list);
        return linkedList;
    }

    public static <E extends Comparable<E>> SortedLinkedList<E> fromCollection(Collection<E> collection) {
        if (Objects.isNull(collection)) throw new IllegalArgumentException("Collection cannot be null");
        var linkedList = new SortedLinkedList<E>();
        linkedList.addAll(collection);
        return linkedList;
    }

    @SafeVarargs
    public static <E extends Comparable<E>> SortedLinkedList<E> of(E... elements) {
        if (Objects.isNull(elements)) throw new IllegalArgumentException("Elements cannot be null");
        SortedLinkedList<E> linkedList = new SortedLinkedList<>();
        linkedList.addAll(Arrays.asList(elements));
        return linkedList;
    }


    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private void forEach(Node<T> current, Consumer<T> consumer) {
        if (Objects.isNull(current)) return;
        consumer.accept(current.data);
        this.forEach(current.next, consumer);
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public boolean contains(T other) {
        if (isEmpty()) return false;

        var current = this.head;

        while (current != null) {
            if (current.data.compareTo(other) == 0) return true;
            current = current.next;
        }

        return false;
    }

    public boolean add(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");
        if (isEmpty()) {
            this.head = new Node<>(data);
            this.tail = this.head;
            this.size++;
            return true;
        }

        // add the data at the end of the list by default
        this.tail.next = new Node<>(data);
        this.tail = this.tail.next;
        this.size++;
        return this.size > 1;
    }

    public boolean remove(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return false;

        if (this.head.data.compareTo(data) == 0) {
            this.head = this.head.next;
            this.size--;
            return true;
        }

        var current = this.head;

        while (current.next != null) {
            if (current.next.data.compareTo(data) == 0) {
                current.next = current.next.next;
                this.size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        if (Objects.isNull(c)) throw new IllegalArgumentException("Collection cannot be null");
        c.forEach(this::add);
        return true;
    }

    public boolean removeAll(Collection<? extends T> c) {
        if (Objects.isNull(c)) throw new IllegalArgumentException("Collection cannot be null");
        c.forEach(this::remove);
        return true;
    }

    public boolean removeIf(Predicate<? super T> filter) {
        if (Objects.isNull(filter)) throw new IllegalArgumentException("Filter cannot be null");
        var current = this.head;

        while (current != null) {
            if (filter.test(current.data)) {
                this.remove(current.data);
            }
            current = current.next;
        }

        return true;
    }

    public int size() {
        return this.size;
    }

    public void quickSort() {
        this.quickSort(0, this.size - 1);
    }

    public void shellSort() {
        int h = 1;
        int n = this.size;

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

    private T get(int index) {
        var current = this.head;
        int i = 0;

        while (i < index) {
            current = current.next;
            i++;
        }

        if (Objects.isNull(current)) throw new IllegalArgumentException("Index out of bounds");

        return current.data;
    }

    private void set(int index, T value) {
        var current = this.head;
        int i = 0;

        while (i < index) {
            current = current.next;
            i++;
        }

        if (Objects.isNull(current)) throw new IllegalArgumentException("Index out of bounds");

        current.data = value;
    }

    private void swap(int i, int j) {
        T temp = get(i);
        set(i, get(j));
        set(j, temp);
    }

    @Override
    public String toString() {
        var current = this.head;
        var builder = new StringBuilder();

        while (current != null) {
            builder.append(current.data).append(" -> "); // "1 -> 2 -> 3 -> 4 -> 5 -> null"
            current = current.next;
        }

        builder.append("null");
        return builder.toString();
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
