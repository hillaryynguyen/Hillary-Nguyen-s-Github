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
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds");
        }
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;

            int siteIndex = (row - 1) * gridSize + col;

            if (row == 1) {
                uf.union(siteIndex, virtualTopSite);
            }
            if (row == gridSize) {
                uf.union(siteIndex, virtualBottomSite);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(siteIndex, (row - 2) * gridSize + col);
            }
            if (row < gridSize && isOpen(row + 1, col)) {
                uf.union(siteIndex, row * gridSize + col);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(siteIndex, (row - 1) * gridSize + col - 1);
            }
            if (col < gridSize && isOpen(row, col + 1)) {
                uf.union(siteIndex, (row - 1) * gridSize + col + 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds.");
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Row and col indices are out of bounds");
        }
        return uf.connected((row -1) * gridSize + col, virtualTopSite);
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


    //method to test Percolation class
    public static void main(String[] args) {
        int gridSize = 5; // Replace with your desired grid size
        Percolation percolation = new Percolation(gridSize);

        percolation.open(1, 2); // Open the site at row 1, column 2
        boolean isOpen = percolation.isOpen(1, 2); // Check if the site at row 1, column 2 is open
        boolean isFull = percolation.isFull(1, 2); // Check if the site at row 1, column 2 is full

        System.out.println("Is Open: " + isOpen);
        System.out.println("Is Full: " + isFull);
    }
}
