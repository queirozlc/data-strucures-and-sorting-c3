package sorting;

import java.util.*;
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

    // factory methods

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


    /**
     * <h2>
     * Traverse the list with the given data
     * </h2>
     *
     * <p>
     * Implementation of the traversal method. This method will traverse the list with the given data. The traversal method will return the data if the data is found, otherwise, it will return null.
     * </p>
     *
     * @param current  the current node
     * @param consumer the consumer function to be applied at each node
     */
    private void forEach(Node<T> current, Consumer<T> consumer) {
        if (Objects.isNull(current)) return;
        consumer.accept(current.data);
        this.forEach(current.next, consumer);
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
     * Remove elements based on the given predicate
     * </h2>
     *
     * <p>
     * This method will remove the elements that matches with the given predicate. Predicate is a function that receives an element and returns true or false based on the given condition.
     * </p>
     *
     * @param filter function to be applied to check if the element matches with the given condition
     * @return true if the elements were removed, false otherwise
     * @throws IllegalArgumentException if the filter function is null
     * @see java.util.function.Predicate
     */
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
     * Find the given data in the list
     * </h2>
     *
     * <p>
     * This method will find the given data in the list. If the data is found, the method will return an optional of the data, otherwise, it will return an empty optional.
     * </p>
     *
     * @param data the data to be found
     * @return an optional of the data if the data is found, otherwise, it will return an empty optional
     * @throws IllegalArgumentException if the data is null
     * @see java.util.Optional
     */
    public Optional<T> find(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        var current = this.head;

        while (current != null) {
            if (current.data.compareTo(data) == 0) return Optional.of(current.data);
            current = current.next;
        }

        return Optional.empty();
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

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
