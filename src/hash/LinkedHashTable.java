package hash;

import shared.DataStructure;
import sorting.SortedLinkedList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LinkedHashTable<T extends Comparable<T>> implements DataStructure<T>, Serializable {
    private static final int DEFAULT_SIZE = 10;

    private SortedLinkedList<T>[] table;

    private int size;

    private int tableSize;

    public LinkedHashTable(int size) {
        var tempSize = BigDecimal
                .valueOf(size)
                .multiply(BigDecimal.valueOf(1.1))
                .intValue();
        table = new SortedLinkedList[calculatePrime(tempSize)];
        this.size = table.length;
        tableSize = 0;
    }

    public LinkedHashTable() {
        this(DEFAULT_SIZE);
    }

    private static int calculatePrime(int size) {
        int prime = size;
        while (!isPrime(prime)) {
            prime++;
        }
        return prime;
    }

    private static boolean isPrime(int prime) {
        for (int i = 2; i < prime; i++) {
            if (prime % i == 0) return false;
        }
        return true;
    }

    public boolean add(T data) {
        int index = this.hash(data);
        if (this.tableSize + 1 > this.table.length) this.grow();
        if (index > this.table.length) this.grow();

        if (table[index] == null) {
            table[index] = new SortedLinkedList<>();
        }
        table[index].add(data);
        this.tableSize++;
        return true;
    }

    public boolean remove(T data) {
        // shrink's the table if the table size is less than 1/4 of the table length
        if (this.tableSize - 1 < this.table.length / 4) this.shrink();
        int index = this.hash(data);
        if (table[index] == null) {
            return false;
        }

        if (table[index].remove(data)) {
            this.tableSize--;
            return true;
        }

        return false;
    }

    public int size() {
        return this.tableSize;
    }

    @Override
    public boolean contains(T data) {
        int index = this.hash(data);
        if (table[index] == null) {
            return false;
        }

        return table[index].contains(data);
    }

    @Override
    public void clear() {
        this.table = new SortedLinkedList[this.size];
        this.tableSize = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.tableSize == 0;
    }

    public void forEach(Consumer<? super T> consumer) {
        for (var list : this.table) {
            if (list != null) {
                list.forEach(Objects.requireNonNull(consumer));
            }
        }
    }

    public Stream<T> stream() {
        return Arrays.stream(this.table)
                .filter(Objects::nonNull)
                .flatMap(SortedLinkedList::stream);
    }

    private int hash(T data) {
        int hash = Objects.requireNonNull(data).hashCode();
        if (hash < 0) hash *= -1;
        return hash % this.size;
    }

    private void grow() {
        var oldTable = this.table;
        this.table = new SortedLinkedList[this.size * 2];
        this.size = this.table.length;
        this.tableSize = 0;
        for (var list : oldTable) {
            if (list != null) {
                list.forEach(this::add);
            }
        }
    }

    private void shrink() {
        var oldTable = this.table;
        this.table = new SortedLinkedList[this.size / 2];
        this.size = this.table.length;
        this.tableSize = 0;
        for (var list : oldTable) {
            if (list != null) {
                list.forEach(this::add);
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                "hash table: " + Arrays.toString(table) +
                ", size: " + tableSize +
                '}';
    }
}
