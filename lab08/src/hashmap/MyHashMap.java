package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadfactor;
    private int initialcapacity;
    private static final int defaultcapacity = 16;
    private static final double defaultloadfactor = .75;

    /** Constructors */
    public MyHashMap() { this(defaultcapacity, defaultloadfactor); }

    public MyHashMap(int initialCapacity) { this(initialCapacity, defaultloadfactor); }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialcapacity = initialCapacity;
        this.loadfactor = loadFactor;
        this.size = 0;
        buckets = new Collection[initialcapacity];
        for (int i = 0; i < initialcapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public void put(K key, V value) {
        int i = Math.floorMod(key.hashCode(), initialcapacity);
        Collection<Node> b = buckets[i];
        for (Node n : b) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        b.add(new Node(key, value));
        size += 1;
        if ((double) size / initialcapacity > loadfactor) {
            resize();
        }
    }
    public V get(K key) {
        int i = Math.floorMod(key.hashCode(), initialcapacity);
        Collection<Node> b = buckets[i];
        for (Node n : b) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }
    public boolean containsKey(K key) {
        int i = Math.floorMod(key.hashCode(), initialcapacity);
        Collection<Node> b = buckets[i];
        for (Node n : b) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size;
    }
    public void clear() {
        buckets = new Collection[initialcapacity];
        for (int i = 0; i < initialcapacity; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
    }
    public void resize() {
        int newcapacity = initialcapacity * 2;
        Collection<Node>[] newb = new Collection[newcapacity];
        for (int i = 0; i < newcapacity; i++) {
            newb[i] = createBucket();
        }
        for (Collection<Node> b : buckets) {
            if (b != null) {
                for (Node n : b) {
                    int i = Math.floorMod(n.key.hashCode(), newcapacity);
                    newb[i].add(n);
                }
            }
        }
        buckets = newb;
        initialcapacity = newcapacity;
    }
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
