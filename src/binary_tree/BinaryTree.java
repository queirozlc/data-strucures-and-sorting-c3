package binary_tree;

import shared.Tree;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    private Node root;
    private int size;

    public BinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean add(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) {
            this.root = new Node(data);
            this.size++;
            return true;
        }

        var result = this.insert(this.root, data);

        if (result) this.size++;

        return result;
    }

    @Override
    public boolean remove(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return false;

        var result = this.delete(this.root, data).isPresent();

        if (result) this.size--;

        return result;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return false;

        return Objects.nonNull(this.traversal(data));
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * <h2>
     * Search method - This Method perform an in order search in the tree
     * </h2>
     *
     * <p>
     * This method perform a in order search in the tree, returning the index of the value in the tree. If the value is not in the tree, the method returns -1.
     * </p>
     *
     * @param data value to be searched
     * @return the index of the value in the tree
     * @see <a href="https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR)">In order search</a>
     */
    @Override
    public T traversal(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return null;

        return this.traversalInOrder(this.root, data);
    }

    /**
     * <h2>
     * Search method - This Method perform a pre order search in the tree
     * </h2>
     *
     * <p>
     * This method perform a pre order search in the tree, returning an optional of the value to be searched.
     * </p>
     *
     * <p>
     * Pre order search: root -> left -> right
     * </p>
     *
     * @param data value to be searched
     * @return Optional<T> An optional of the value to be searched
     * @see java.util.Optional
     * @see <a href="https://en.wikipedia.org/wiki/Tree_traversal#Pre-order_(NLR)">Pre order search</a>
     */
    public Optional<T> searchPreOrder(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return null;

        return this.traversalPreOrder(this.root, data);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * <h2>
     * Recursive delete method
     * </h2>
     *
     * <p>
     * This method perform a recursive delete in the tree, returning an optional of the node to be deleted.
     * </p>
     *
     * @param node current node
     * @param data value to be deleted
     * @return Optional<Node> node to be deleted
     * @see java.util.Optional
     */
    private Optional<Node> delete(Node node, T data) {
        if (Objects.isNull(node)) return Optional.empty();

        if (node.biggerThan(data)) {
            node.addLeft(this.delete(node.left, data).orElse(null));
            return Optional.of(node);
        }

        if (node.lowerThan(data)) {
            node.addRight(this.delete(node.right, data).orElse(null));
            return Optional.of(node);
        }

        if (Objects.isNull(node.left)) return Optional.ofNullable(node.right);

        if (Objects.isNull(node.right)) {
            return Optional.of(node.left);
        }

        node.addLeft(this.findMax(node, node.left));
        return Optional.of(node);
    }

    private Node findMax(Node node, Node max) {
        if (Objects.isNull(max.right)) {
            node.setValue(max.value);
            return max.left;
        }

        max.addRight(this.findMax(node, max.right));
        return max;
    }

    private boolean insert(Node node, T data) {
        if (node.equals(data)) return false;

        if (node.biggerThan(data)) {
            if (Objects.isNull(node.left)) {
                node.addLeft(new Node(data));
                return true;
            }

            return this.insert(node.left, data);
        }

        if (Objects.isNull(node.right)) {
            node.addRight(new Node(data));
            return true;
        }

        return this.insert(node.right, data);
    }

    /**
     * <h2>
     * Recursive in order traversal
     * </h2>
     * <p>
     * In order search: left -> root -> right
     * </p>
     *
     * @param node current node
     * @param data value to be searched
     * @return T value to be searched
     */
    private T traversalInOrder(Node node, T data) {
        if (Objects.isNull(node)) return null;

        var left = this.traversalInOrder(node.left, data);

        if (Objects.nonNull(left)) return left;

        if (node.equals(data)) return node.value;

        return this.traversalInOrder(node.right, data);
    }

    /**
     * <h2>
     * Recursive pre order traversal
     * </h2>
     *
     * <p>
     * Pre order search: root -> left -> right
     * </p>
     *
     * @param node current node
     * @param data value to be searched
     * @return Optional<T> An optional of the value to be searched
     * @see java.util.Optional
     */
    private Optional<T> traversalPreOrder(Node node, T data) {
        if (Objects.isNull(node)) return Optional.empty();

        if (node.equals(data)) return Optional.of(node.value);

        var left = this.traversalPreOrder(node.left, data);

        if (left.isPresent()) return left;

        return this.traversalPreOrder(node.right, data);
    }

    /**
     * <h2>
     * Recursive post order traversal
     * </h2>
     *
     * <p>
     * Post order search: left -> right -> root
     * </p>
     *
     * @param node current node
     * @param data value to be searched
     * @return Optional<T> An optional of the value to be searched
     * @see java.util.Optional
     */

    private Optional<T> traversalPostOrder(Node node, T data) {
        if (Objects.isNull(node)) return Optional.empty();

        var left = this.traversalPostOrder(node.left, data);

        if (left.isPresent()) return left;

        var right = this.traversalPostOrder(node.right, data);

        if (right.isPresent()) return right;

        if (node.equals(data)) return Optional.of(node.value);

        return Optional.empty();
    }

    @Override
    public String toString() {
        // build a string representation of the tree
        StringBuilder sb = new StringBuilder();
        this.buildString(this.root, sb);
        return sb.toString();
    }

    private void buildString(Node root, StringBuilder sb) {
        // build with circles to and arrows to represent the tree
        if (Objects.isNull(root)) return;
        sb.append(root.value).append(" ");
        if (Objects.nonNull(root.left)) {
            sb.append("left -> ").append(root.left.value).append(" ");
        }
        if (Objects.nonNull(root.right)) {
            sb.append("right -> ").append(root.right.value).append(" ");
        }
        sb.append("\n");
        this.buildString(root.left, sb);
        this.buildString(root.right, sb);
    }

    /**
     * <h2>
     * Perform a in order traversal in the tree applying the consumer function
     * </h2>
     *
     * <p>
     * The in order traversal flow is: left -> root -> right
     * </p>
     *
     * <p>
     * For each node in the tree, the consumer function will be applied to execute some logic
     * </p>
     *
     * <p>
     * Example:
     * </p>
     *
     * <pre>
     *     {@code
     *     var tree = new BinaryTree<Integer>();
     *     tree.add(5);
     *     tree.add(3);
     *     tree.add(7);
     *     tree.add(2);
     *     tree.add(4);
     *     tree.add(6);
     *     tree.add(8);
     *     tree.inOrderTraversal(System.out::println);
     *     }
     * </pre>
     *
     * <p>
     * Consumer functions could be exemplified by the {@code forEach} method of the {@code java.util.stream.Stream} class
     * </p>
     *
     * @param consumer function to be applied
     * @see java.util.function.Consumer
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEach-java.util.function.Consumer-">Stream forEach</a>
     * @see <a href="https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR)">In order traversal</a>
     * @see java.util.stream.Stream
     */
    public void inOrderTraversal(Consumer<T> consumer) {
        this.inOrderTraversal(this.root, consumer);
    }

    private void inOrderTraversal(Node root, Consumer<T> consumer) {
        if (Objects.isNull(root)) return;
        this.inOrderTraversal(root.left, consumer);
        consumer.accept(root.value);
        this.inOrderTraversal(root.right, consumer);
    }

    private class Node {
        private T value;
        private Node left;
        private Node right;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public boolean lowerThan(T data) {
            return this.value.compareTo(data) < 0;
        }

        public boolean biggerThan(T data) {
            return this.value.compareTo(data) > 0;
        }

        public boolean equals(T data) {
            return this.value.compareTo(data) == 0;
        }

        public void addLeft(Node node) {
            this.left = node;
        }

        public void addRight(Node node) {
            this.right = node;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
