import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private int openSitesCount;
    private final int gridSize;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF fullCheckUf;
    private final int virtualTopSite;
    private final int virtualBottomSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        gridSize = N;
        openSites = new boolean[N * N];
        openSitesCount = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        fullCheckUf = new WeightedQuickUnionUF(N * N + 1); // Exclude virtualBottomSite
        virtualTopSite = N * N;
        virtualBottomSite = N * N + 1;

        if (N > 1) {
            // Connect virtual top and bottom sites to their respective rows
            for (int col = 0; col < N; col++) {
                uf.union(virtualTopSite, col);
                uf.union(virtualBottomSite, (N - 1) * N + col);
                fullCheckUf.union(virtualTopSite, col);
            }
        } else {
            // For N=1, connect virtual top and bottom sites to the single site
            uf.union(virtualTopSite, 0);
            uf.union(virtualBottomSite, 0);
            fullCheckUf.union(virtualTopSite, 0);
        }
    }


    public void open(int row, int col) {
        validateIndices(row, col);
        int siteIndex = getSiteIndex(row, col);
        if (!openSites[siteIndex]) {
            openSites[siteIndex] = true;
            openSitesCount++;

            if (row == 0) {
                uf.union(siteIndex, virtualTopSite);
                fullCheckUf.union(siteIndex, virtualTopSite);
            }
            if (row == gridSize - 1) {
                uf.union(siteIndex, virtualBottomSite);
            }

            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];
                if (isValid(newRow, newCol) && isOpen(newRow, newCol)) {
                    int neighborIndex = getSiteIndex(newRow, newCol);
                    uf.union(siteIndex, neighborIndex);
                    fullCheckUf.union(siteIndex, neighborIndex);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openSites[getSiteIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        int siteIndex = getSiteIndex(row, col);
        return isOpen(row, col) && fullCheckUf.connected(siteIndex, virtualTopSite);
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        if (gridSize == 1) {
            // Special case for N = 1
            return isOpen(0, 0);
        }
        return uf.connected(virtualTopSite, virtualBottomSite);
    }

    private int getSiteIndex(int row, int col) {
        return row * gridSize + col;
    }

    private void validateIndices(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IndexOutOfBoundsException("Row and col indices are out of bounds.");
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }
}

