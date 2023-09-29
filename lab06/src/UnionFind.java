public class UnionFind {
    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     * You can assume that we are only working with non-negative integers as the items
     * in our disjoint sets.
     */
    private int[] data;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than zero.");
        }
        data = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int root = find(v);
        return -data[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= data.length) {
            throw new IllegalArgumentException("Invalid item.");
        }
        return data[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= data.length) {
            throw new IllegalArgumentException("Invalid item.");
        }
        if (data[v] < 0) {
            return v;
        } else {
            int root = find(data[v]);
            data[v] = root;
            return root;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int root1 = find(v1);
        int root2 = find(v2);

        if (root1 != root2) {
            if (data[root1] < data[root2]) {
                data[root1] += data[root2];
                data[root2] = root1;
            } else {
                data[root2] += data[root1];
                data[root1] = root2;
            }
        }
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }
}
