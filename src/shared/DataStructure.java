package shared;

public interface DataStructure<E extends Comparable<E>> {

    boolean add(E data);

    boolean remove(E data);

    int size();

    boolean contains(E data);

    void clear();

    boolean isEmpty();
}
