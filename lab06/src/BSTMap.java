import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private Node root;
    private int size;

    @Override
    public void put(K key, V value) {
        root = putNode(root, key, value);
    }

    public Node putNode(Node node, K key, V value) {
        if (node == null) {
            size += 1;
            return new Node(key, value);
        }
        int check = key.compareTo(node.key);
        if (check < 0) {
            node.left = putNode(node.left, key, value);
        }
        else if (check > 0) {
            node.right = putNode(node.right, key, value);
        }
        else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return getNode(root, key);
    }

    public V getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        int check = key.compareTo(node.key);
        if (check < 0) {
            return getNode(node.left, key);
        }
        else if (check > 0) {
            return getNode(node.right, key);
        }
        else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsnode(root, key);
    }

    public boolean containsnode(Node node, K key) {
        if (node == null || key == null) {
            return false;
        }
        int check = key.compareTo(node.key);
        if (check < 0) {
            return containsnode(node.left, key);
        }
        else if (check > 0) {
            return containsnode(node.right, key);
        }
        else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
