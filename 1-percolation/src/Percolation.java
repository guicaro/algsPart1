/*----------------------------------------------------------------
 *  Author:        Guillermo Cabrera
 *  Written:       Nov 7, 2016
 *  Last updated:
 *
 *  Compilation:   javac XXX
 *  Execution:     java XXX
 *
 *  Description goes here
 *
 *  % java HelloWorld
 *  Hello, World
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean [] storage;
    public WeightedQuickUnionUF qu;
    private int gridSize;
    private int totalSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException("size should be greater than 0");

        totalSites = n * n + 2;               // Includes 2 virtual sites
        gridSize = n;
        storage = new boolean [totalSites];
        qu = new WeightedQuickUnionUF(totalSites);

        // All sites blocked
        for( int x = 0; x < totalSites; x++) {
            storage[x] = false;
        }

        // Connect the virtual sites to first row and last row
        // They will be connected but not OPEN.

        // Connect the top virtual site to first row
        for ( int x = 1; x <= gridSize; x++)
            qu.union(0, xyTo1D( x, 1));

        // Connect the bottom virtual site to last row
        for ( int x = 1; x <= gridSize; x++)
            qu.union(totalSites - 1, xyTo1D( x, gridSize) );

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!isInLimits(row, col))
            throw new IndexOutOfBoundsException("row index i out of bounds");

        storage [ xyTo1D(row,col) ] = true;

        if ( isInLimits(row-1, col) && isOpen(row - 1, col ))  //north
            qu.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        if ( isInLimits(row, col+1) && isOpen(row, col + 1 ))  //east
            qu.union(xyTo1D(row, col), xyTo1D(row, col+1));
        if ( isInLimits(row+1, col) && isOpen( row + 1, col))  //south
            qu.union(xyTo1D(row, col), xyTo1D(row+1, col));
        if ( isInLimits(row, col-1) && isOpen( row, col - 1))   //west
            qu.union(xyTo1D(row, col), xyTo1D(row, col-1));

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (!isInLimits(row, col))
            throw new IndexOutOfBoundsException("row index i out of bounds");

        return storage[ xyTo1D(row, col) ] == true;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {

        if (!isInLimits(row, col))
            throw new IndexOutOfBoundsException("row index i out of bounds");

        // A full site is an open site that can be connected to an open site in the top row
        // via a chain of neighboring (left, right, up, down) open sites.

        return isOpen(row, col) && (  isInLimits(row - 1, col) && isOpen( row - 1, col ) ||
                                      isInLimits(row, col + 1) && isOpen( row, col + 1 ) ||
                                      isInLimits(row + 1, col) && isOpen( row + 1, col ) ||
                                      isInLimits(row, col - 1) && isOpen( row, col - 1 ) );

    }

    // does the system percolate?
    // if we fill all open sites connected to the top row and that process fills some open site on the bottom row.

    // We say the system percolates
    // if there is a full site in the bottom row. In other words, a system percolates if we fill
    // all open sites connected to the top row and that process fills some open site on the bottom row.
    public boolean percolates() {

        for ( int x = 1; x <= gridSize; x++) {
            if (isFull(x, gridSize))
                return true;
        }

        return false;

    }

    public int xyTo1D(int row, int col) {

        if (!isInLimits(row, col))
            throw new IndexOutOfBoundsException("row index i out of bounds");

        return ((gridSize * row) + col) - gridSize ;
    }

    private boolean isInLimits(int row, int col) {

        return !((row <= 0 || row > gridSize) || (col <= 0 || col > gridSize));
    }

    public static void main(String[] args) {

        // Test the open() method and the Percolation() constructor.
        // Percolation testP = new Percolation(3);
        // testP.open(1,1);
        // testP.open(1,2);
        // System.out.println(testP.qu.connected(1,2));

        // TODO: Fix input1.txt, input2-no.png
        // TODO: We should make the first site to be full when it is opened.

        // TODO 3: Check style and findbugs on commandline.
        //Percolation test = new Percolation(5);
    }
}