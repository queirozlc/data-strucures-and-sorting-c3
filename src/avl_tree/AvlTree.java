package avl_tree;

import shared.Tree;

import java.util.Objects;
import java.util.Optional;

public class AvlTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;
    private int size;


    @Override
    public boolean insert(T data) {
        if (Objects.isNull(data)) throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty()) {
            this.root = new Node<>(data);
            this.size++;
            return true;
        }

        return this.insert(this.root, data).map(node -> {
            this.root = node;
            this.size++;
            return true;
        }).orElse(false);
    }

    @Override
    public boolean delete(T data) {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T data) {
        return false;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public T search(T data) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * <h2>
     * Insert a new node in the tree
     * </h2>
     *
     * <p>
     * Avl tree's insert method is a little bit different from the binary tree's insert method.
     * Since the avl tree is a self-balancing tree, we need to check if the tree is balanced after
     * inserting a new node.
     * </p>
     *
     * @param node The node to be compared
     * @param data The data to be inserted
     * @return Optional of the new node
     * @see <a href="https://www.cs.usfca.edu/~galles/visualization/AVLtree.html">AVL Tree Visualization</a>
     * @see java.util.Optional
     */
    private Optional<Node<T>> insert(Node<T> node, T data) {
        if (Objects.isNull(node)) return Optional.empty();

        if (node.biggerThan(data)) {
            node.left = this.insert(node.left, data).orElseGet(() -> new Node<>(data));
        } else if (node.data.compareTo(data) < 0) {
            node.right = this.insert(node.right, data).orElseGet(() -> new Node<>(data));
        } else {
            return Optional.empty();
        }

        node.updateHeight();
        return applyRotation(node);
    }

    private Optional<Node<T>> applyRotation(Node<T> node) {
        int balance = node.height(node.left) - node.height(node.right);

        if (balance > 1) {
            // left-left case
            if (node.height(node.left.left) < node.height(node.left.right)) {
                // left right case
                node.left = leftRotation(node.left);
            }
            return Optional.of(rightRotation(node));
        }

        if (balance < -1) {
            // right-right case
            if (node.height(node.right.right) < node.height(node.right.left)) {
                // right left case
                node.right = rightRotation(node.right);
            }
            return Optional.of(leftRotation(node));
        }

        return Optional.of(node);
    }

    private Node<T> leftRotation(Node<T> node) {
        Node<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    private Node<T> rightRotation(Node<T> node) {
        Node<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }


    @Override
    public String toString() {
        // build a string representation of the tree
        StringBuilder sb = new StringBuilder();
        this.buildString(this.root, sb);
        return sb.toString();
    }

    private void buildString(Node<T> root, StringBuilder sb) {
        // build with circles to and arrows to represent the tree
        if (Objects.isNull(root)) return;
        sb.append(root.data).append(" ");
        if (Objects.nonNull(root.left)) {
            sb.append("left -> ").append(root.left.data).append(" ");
        }
        if (Objects.nonNull(root.right)) {
            sb.append("right -> ").append(root.right.data).append(" ");
        }
        sb.append("\n");
        this.buildString(root.left, sb);
        this.buildString(root.right, sb);
    }

    private static class Node<T extends Comparable<T>> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        public boolean biggerThan(T data) {
            return this.data.compareTo(data) > 0;
        }

        public void addLeft(Node<T> tNode) {
            this.left = tNode;
        }

        public void addRight(Node<T> tNode) {
            this.right = tNode;
        }

        public void updateHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        public int height(Node<T> node) {
            return Objects.isNull(node) ? 0 : node.height;
        }
    }
}
