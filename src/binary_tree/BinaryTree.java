package binary_tree;

import shared.Tree;

import java.util.Objects;

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
    }

    @Override
    public int search(T data) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
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

    private class Node {
        private final T value;
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
    }
}
