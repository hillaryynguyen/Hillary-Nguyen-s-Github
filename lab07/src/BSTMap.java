import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private class TreeNode {
        K key;
        V value;
        TreeNode left;
        TreeNode right;

        public TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root;
    private int size;

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private TreeNode put(TreeNode node, K key, V value) {
        if (node == null) {
            size++;
            return new TreeNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(TreeNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    private TreeNode getNode(TreeNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNode(node.left, key);
        } else if (cmp > 0) {
            return getNode(node.right, key);
        } else {
            return node;
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
        Set<K> keySet = new HashSet<>();
        collectKeys(root, keySet);
        return null;
    }

    private void collectKeys(TreeNode node, Set<K> keySet) {
        if (node == null) {
            return;
        }
        collectKeys(node.left, keySet);
        keySet.add(node.key);
        collectKeys(node.right, keySet);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Remove method not implemented yet.");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Iterator method not implemented yet.");
    }
}
