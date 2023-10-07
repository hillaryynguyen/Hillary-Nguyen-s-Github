import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private int openSitesCount;
    private final int gridSize;
    private final WeightedQuickUnionUF uf;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final WeightedQuickUnionUF ufTop;
    private final int virtualTopVirtualSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        this.gridSize = N; // Change 'this.N' to 'this.gridSize'
        this.openSites = new boolean[N * N];
        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.ufTop = new WeightedQuickUnionUF(N * N + 1);
        this.virtualTopSite = N * N;
        this.virtualBottomSite = N * N + 1;
        this.virtualTopVirtualSite = N * N;
        this.openSitesCount = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IndexOutOfBoundsException("Row or column index is out of bounds.");
        }

        validateIndices(row, col);

        int siteIndex = getSiteIndex(row, col);

        if (!openSites[siteIndex]) {
            openSites[siteIndex] = true;
            openSitesCount++;

            if (row == 0) {
                uf.union(siteIndex, virtualTopSite);
                ufTop.union(siteIndex, virtualTopVirtualSite); // Connect to ufTop
            }
            if (row == gridSize - 1) {
                uf.union(siteIndex, virtualBottomSite);
            }

            // Connect the site to virtual sites if necessary
            connectToVirtualSites(row, col);

            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];
                if (isValid(newRow, newCol) && isOpen(newRow, newCol)) {
                    int neighborIndex = getSiteIndex(newRow, newCol);
                    uf.union(siteIndex, neighborIndex);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openSites[getSiteIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        /**validateIndices(row, col);
        int siteIndex = getSiteIndex(row, col);
        return isOpen(row, col) && uf.connected(siteIndex, virtualTopSite) && uf.connected(siteIndex, virtualBottomSite);*/
        validateIndices(row, col);
        int siteIndex = getSiteIndex(row, col);

        // Check if the site is open and connected to the virtual top site using ufTop
        boolean isOpenAndConnectedToTop = isOpen(row, col) && ufTop.connected(siteIndex, virtualTopVirtualSite);

        // Return true if both conditions are met
        boolean isFull = isOpenAndConnectedToTop;

        System.out.println("Site (" + row + ", " + col + ") isFull: " + isFull);
        return isFull;
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        boolean percolates = uf.connected(virtualTopSite, virtualBottomSite);
        System.out.println("Percolates: " + percolates);

        // Debugging output to show the state of connections
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int siteIndex = getSiteIndex(i, j);
                System.out.println("Site (" + i + ", " + j + ") connected to virtualTopSite: " + uf.connected(siteIndex, virtualTopSite));
                System.out.println("Site (" + i + ", " + j + ") connected to virtualBottomSite: " + uf.connected(siteIndex, virtualBottomSite));
            }
        }

        return percolates;
    }

    private int getSiteIndex(int row, int col) {
        return row * gridSize + col;
    }

    private void connectToNeighbors(int row, int col) {
        int siteIndex = getSiteIndex(row, col);

        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(siteIndex, getSiteIndex(row - 1, col));
        }
        if (row < gridSize - 1 && isOpen(row + 1, col)) {
            uf.union(siteIndex, getSiteIndex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(siteIndex, getSiteIndex(row, col - 1));
        }
        if (col < gridSize - 1 && isOpen(row, col + 1)) {
            uf.union(siteIndex, getSiteIndex(row, col + 1));
        }
    }

    private void validateIndices(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IndexOutOfBoundsException("Row or col indices are out of bounds.");
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    private void connectToVirtualSites(int row, int col) {
        int siteIndex = getSiteIndex(row, col);

        if (row == 0) {
            // Connect the site to the virtualTopSite if it's in the top row
            uf.union(siteIndex, virtualTopSite);
        }
        if (row == gridSize - 1) {
            // Connect the site to the virtualBottomSite if it's in the bottom row
            uf.union(siteIndex, virtualBottomSite);
        }
    }
}
