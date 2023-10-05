import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] grid;
    private int openSites;
    private final int gridSize;
    private final WeightedQuickUnionUF uf;
    private final int virtualTopSite;
    private final int virtualBottomSite;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        gridSize = N;
        grid = new boolean[N][N];
        openSites = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        virtualTopSite = 0;
        virtualBottomSite = N * N + 1;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.

        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds");
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;

            int siteIndex = (row) * gridSize + col + 1;

            if (row == 0) {
                uf.union(siteIndex, virtualTopSite);
            }
            if (row == gridSize -1) {
                uf.union(siteIndex, virtualBottomSite);
            }
            if (row > 0 && isOpen(row - 1, col)) {
                uf.union(siteIndex, siteIndex - gridSize);
            }
            if (row < gridSize - 1 && isOpen(row + 1, col)) {
                uf.union(siteIndex, siteIndex + gridSize);
            }
            if (col > 0 && isOpen(row, col - 1)) {
                uf.union(siteIndex, siteIndex - 1);
            }
            if (col < gridSize -1  && isOpen(row, col + 1)) {
                uf.union(siteIndex, siteIndex + 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds.");
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds");
        }
        return isOpen(row,col) && uf.connected(row * gridSize + col + 1, virtualTopSite);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openSites;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.connected(virtualTopSite, virtualBottomSite);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
