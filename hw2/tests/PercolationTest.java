import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import edu.princeton.cs.algs4.StdRandom;



public class PercolationTest {
    private int[][] getState(int N, Percolation p) {
        int[][] state = new int[5][5];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = open + full;
            }
        }
        return state;
    }
    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(5);
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        int[][] expectedState = {
                {0, 3, 0, 0, 0},
                {3, 3, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void yourTestHere() {
        // TODO: write some more tests
        //method to test Percolation class
    }

    @Test
    public void testPercolatesWithSingleOpenSite() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(1, 1);
        assertThat(p.percolates()).isFalse(); // Only one open site, should not percolate
    }

    @Test
    public void testPercolatesWithFullGrid() {
        int N = 4;
        Percolation p = new Percolation(N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                p.open(i, j);
            }
        }
        assertThat(p.percolates()).isTrue(); // Full grid should percolate
    }

    @Test
    public void testPercolatesWithBlockedPath() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 1);


        assertThat(p.percolates()).isFalse(); // Path is blocked
    }


    @Test
    public void testPercolatesWithOpenPath() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        assertThat(p.percolates()).isTrue(); // Path is open
    }

    @Test
    public void testBackwashException() {
        Percolation percolation = new Percolation(3);

        // Open some sites that would cause backwash
        percolation.open(0, 0);
        percolation.open(1, 0);
        percolation.open(2, 0);

        // These sites should not be full due to backwash
        assertFalse(percolation.isFull(2, 0));
        assertFalse(percolation.isFull(2, 1));
        assertFalse(percolation.isFull(2, 2));

        // The site (2, 0) should not be open
        assertFalse(percolation.isOpen(2, 0));

        // All other sites should be open and full
        assertTrue(percolation.isOpen(0, 0));
        assertTrue(percolation.isOpen(1, 0));
        assertTrue(percolation.isOpen(2, 1));
        assertTrue(percolation.isOpen(2, 2));

        assertTrue(percolation.isFull(0, 0));
        assertTrue(percolation.isFull(1, 0));
        assertTrue(percolation.isFull(2, 1));
        assertTrue(percolation.isFull(2, 2));

        // The system should not percolate
        assertFalse(percolation.percolates());
    }

    @Test
    public void testRandom() {
        int n = 5; // Change this to the desired grid size

        Percolation percolation = new Percolation(n);

        // Open a random set of sites
        for (int i = 0; i < n * n; i++) {
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            percolation.open(row, col);
        }

        // Check if the system percolates
        boolean percolates = percolation.percolates();
    }

}


