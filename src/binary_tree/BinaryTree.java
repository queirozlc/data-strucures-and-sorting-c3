package binary_tree;

import shared.Tree;

import java.util.Objects;
import java.util.Optional;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    private Node root;
    private int size;

    public BinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean insert(T data) {
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
    public boolean delete(T data) {
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

        return Objects.nonNull(this.search(data));
    }

    @Override
    public void clear() {
        this.root = null;
    }

    /**
     * <h2>
     * Search method - This Method perform a in order search in the tree
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
    public T search(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) return null;

        return this.traversalInOrder(this.root, data);
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
