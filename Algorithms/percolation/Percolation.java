import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF connection;

    private boolean[] map;

    private int numberOfOpenSites = 0;

    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        connection = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        map = new boolean[n * n + 1];
        for (int i = 0; i <= n * n; i++) {
            map[i] = false;
        }
        for (int i = 1; i <= n; i++) {
            connection.union(0, i);
            connection.union(n * n + 1, n * n + 1 - i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (map[getIndex(row, col)]) return;
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
        int index = getIndex(row, col);
        map[index] = true;
        numberOfOpenSites += 1;
        if (col != n && map[index + 1]) connection.union(index, index + 1);
        if (col != 1 && map[index - 1]) connection.union(index, index - 1);
        if (row != n && map[index + n]) connection.union(index, index + n);
        if (row != 1 && map[index - n]) connection.union(index, index - n);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
        return map[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
        return isOpen(row, col) && connection.find(getIndex(row, col)) == connection.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connection.find(0) == connection.find(n * n + 1);
    }

    private int getIndex(int row, int column) {
        if (row <= 0 || row > n || column <= 0 || column > n) throw new IllegalArgumentException();
        return (row - 1) * n + column;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
