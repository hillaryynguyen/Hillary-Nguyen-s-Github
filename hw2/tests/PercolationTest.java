import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

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
}
