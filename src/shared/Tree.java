package shared;

/**
 * <h2>
 * Tree interface
 * </h2>
 *
 * <p>
 * This interface is used to define the methods that a tree must have. In this program, we going to use the AVL tree and the B-tree.
 * </p>
 *
 * @param <T> generic type
 * @author lucasqueiroz
 */
public interface Tree<T> {

    boolean insert(T data);

    boolean delete(T data);

    int size();

    boolean contains(T data);

    void clear();

    int search(T data);
}