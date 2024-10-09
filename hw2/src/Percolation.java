import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int size; //size of grid
    private int opensitesnum = 0; // number of open sites
    private boolean[] opensites; // keeps track of open sites
    private int virtualtop;
    private int virtualbottom;
    private WeightedQuickUnionUF wquf;
    private WeightedQuickUnionUF fullwquf;

    //create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        opensites = new boolean[size * size];
        virtualtop = size * size;
        virtualbottom = size * size + 1;
        wquf = new WeightedQuickUnionUF(size * size + 2);
        fullwquf = new WeightedQuickUnionUF(size * size + 1);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validindex(row, col);
        int i = xyto1D(row, col);
        if (!isOpen(row, col)) {
            opensites[i] = true;
            opensitesnum += 1;
            // if it is at virtual top say its full
            if (row == 0) {
                wquf.union(virtualtop, i);
                fullwquf.union(virtualtop, i);
            }
            connections(row, col);
            if (row == size - 1) {
                wquf.union(virtualbottom, i);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validindex(row, col);
        int i = xyto1D(row, col);
        return opensites[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validindex(row, col);
        int i = xyto1D(row, col);
        return fullwquf.connected(virtualtop, i);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return opensitesnum;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.connected(virtualtop, virtualbottom);
    }

    public int xyto1D(int r, int c) {
        return ((r + 1) * size) + (c + 1);
    }

    public void validindex(int r, int c) {
        if (r < 0 || c < 0 || r > size || c > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: row=" + r);
        }
    }

    public void connections(int r, int c) {
        int i = xyto1D(r, c);
        if (isOpen(r, c - 1) && c > 0) {
            wquf.union(xyto1D(r, c - 1), i);
            fullwquf.union(xyto1D(r, c - 1), i);
        }
        if (isOpen(r, c + 1) && c <= size - 1) {
            wquf.union(xyto1D(r, c + 1), i);
            fullwquf.union(xyto1D(r, c + 1), i);
        }
        if (isOpen(r - 1, c) && r > 0) {
            wquf.union(xyto1D(r - 1, c), i);
            fullwquf.union(xyto1D(r - 1, c), i);
        }
        if (isOpen(r + 1, c) && r <= size - 1) {
            wquf.union(xyto1D(r + 1, c), i);
            fullwquf.union(xyto1D(r + 1, c), i);
        }
    }

}
