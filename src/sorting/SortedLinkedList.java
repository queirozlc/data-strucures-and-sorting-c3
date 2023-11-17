package sorting;

import shared.DataStructure;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SortedLinkedList<T extends Comparable<T>> implements DataStructure<T> {
    private static final Random RANDOM = new Random();

    private Node<T> head;
    private int size;
    private Node<T> tail;

    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    /**
     * <h2>
     * Clear the list
     * </h2>
     *
     * <p>
     * This method will clear the list. Since linked list structure does not work with indexes and the elements are linked, the garbage collector will remove the elements from the memory. The size of the list is set to zero.
     * </p>
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * <h2>
     * Check if the list contains the given data
     * </h2>
     *
     * <p>
     * This method will check if the list contains the given data. If the list is empty, the method will return false. If the list is not empty, the method will iterate over the list until the data is found. If the data is found, the method will return true, otherwise, it will return false.
     * </p>
     *
     * @param other the data to be checked
     * @return true if the list contains the given data, false otherwise
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T other) {
        if (Objects.isNull(other)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return false;

        var current = this.head;

        while (current != null) {
            if (current.data.compareTo(other) == 0) return true;
            current = current.next;
        }

        return false;
    }

    /**
     * <h2>
     * Add a new element to the list
     * </h2>
     *
     * <p>
     * This method will add a new element to the list. The element will be added by default at the end of the list. The list can be sorted using the quick sort or the shell sort method.
     * </p>
     *
     * @param data the data to be added
     * @return true if the data was added, false otherwise
     * @throws IllegalArgumentException if the data is null
     */
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

    /**
     * <h2>
     * Stream the list
     * </h2>
     *
     * <p>
     * Java Stream API is a very powerful tool to work with collections. This method will return a stream of the list to be able to use the Stream API for handle functional operations in the list.
     * </p>
     *
     * @return a stream of the list
     * @see java.util.stream.Stream Stream API
     */
    public Stream<T> stream() {
        return Stream.iterate(this.head, Objects::nonNull, node -> node.next).map(node -> node.data);
    }

    /**
     * <h2>
     * Remove the given data from the list
     * </h2>
     *
     * <p>
     * This method will remove the given data from the list. If the data is the head of the list, the head will be removed and the next element will be the new head. If the data is the tail of the list, the tail will be removed and the previous element will be the new tail. If the data is in the middle of the list, the element will be removed and the previous element will point to the next element.
     * </p>
     *
     * @param data the data to be removed
     * @return true if the data was removed, false otherwise
     * @throws IllegalArgumentException if the data is null
     */
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

    /**
     * <h2>
     * Add all the elements from the given collection to the list
     * </h2>
     *
     * @param c the collection to be added
     * @return true if the collection was added, false otherwise
     */
    public boolean addAll(Collection<? extends T> c) {
        if (Objects.isNull(c)) throw new IllegalArgumentException("Collection cannot be null");
        c.forEach(this::add);
        return true;
    }

    /**
     * <h2>
     * Get the size of the list
     * </h2>
     *
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }

    public void quickSort() {
        this.quickSort(0, this.size - 1);
    }

    public void shellSort() {
        int i;
        int j;
        int h;
        T temp;
        h = 1;
        do {
            h = 3 * h + 1;
        } while (h < this.size);
        do {
            h = h / 3;
            for (i = h; i < this.size; i++) {
                temp = this.get(i);
                j = i;
                while (this.get(j - h).compareTo(temp) > 0) {
                    this.set(j, this.get(j - h));
                    j = j - h;
                    if (j < h) {
                        break;
                    }
                }
                this.set(j, temp);
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

    /**
     * <h2>
     * Get the element at the given index in the list
     * </h2>
     *
     * <p>
     * Iterate over the list until the given index and return the element at the given index. If the index is out of bounds, the method will throw an exception.
     * </p>
     *
     * @param index the index to be got
     * @return the element at the given index
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public T get(int index) {
        var current = this.head;
        int i = 0;

        while (i < index) {
            current = current.next;
            i++;
        }

        if (Objects.isNull(current)) throw new IllegalArgumentException("Index out of bounds");

        return current.data;
    }

    /**
     * <h2>
     * Set the given value at the given index
     * </h2>
     *
     * <p>
     * This method will set the given value at the given index. If the index is out of bounds, the method will throw an exception. The method will not change any pointer of the list, it will only change the value of the node.
     * </p>
     *
     * @param index the index to be set
     * @param value the value to be set
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public void set(int index, T value) {
        var current = this.head;
        int i = 0;

        while (i < index) {
            current = current.next;
            i++;
        }

        if (Objects.isNull(current)) throw new IllegalArgumentException("Index out of bounds");

        current.data = value;
    }

    /**
     * <h2>
     * Swap the current elements at the given indexes
     * </h2>
     *
     * <p>
     * This method will get the elements at the given indexes and swap them.
     * </p>
     *
     * @param i the first index
     * @param j the second index
     */
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

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        var current = this.head;

        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }

    public T[] toArray() {
        var array = (T[]) new Comparable[this.size];
        var current = this.head;
        int i = 0;

        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }

        return array;
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
