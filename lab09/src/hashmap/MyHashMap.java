package hashmap;

import java.util.Collection;
import java.util.HashSet;
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
    private double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = createBuckets(initialCapacity);
        this.loadFactor = loadFactor;
    }



    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
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
        return new LinkedList<>();
    }

    private Collection<Node>[] createBuckets(int capacity) {
        @SuppressWarnings("unchecked")
        Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[capacity];
        return newBuckets;
    }



    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    private int hash(K key, int capacity) {
        int hash = key.hashCode();
        return Math.floorMod(hash, capacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i].clear();
            }
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketIndex = hash(key, buckets.length);
        if (buckets[bucketIndex] == null) {
            return false;
        }
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int bucketIndex = hash(key, buckets.length);
        if (buckets[bucketIndex] == null) {
            return null;
        }
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int bucketIndex = hash(key, buckets.length);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = createBucket();
        }
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[bucketIndex].add(new Node(key, value));
        size++;
        if ((double) size / buckets.length > loadFactor) {
            resize();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    keySet.add(node.key);
                }
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int bucketIndex = hash(key, buckets.length);
        if (buckets[bucketIndex] == null) {
            return null;
        }
        Iterator<Node> iterator = buckets[bucketIndex].iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                iterator.remove();
                size--;
                return node.value;
            }
        }
        return null;
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        Collection<Node>[] newBuckets = createBuckets(newCapacity);

        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    int bucketIndex = hash(node.key, newCapacity);
                    if (newBuckets[bucketIndex] == null) {
                        newBuckets[bucketIndex] = createBucket();
                    }
                    newBuckets[bucketIndex].add(node);
                }
            }
        }

        buckets = newBuckets;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}









