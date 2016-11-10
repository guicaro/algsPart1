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
    private WeightedQuickUnionUF qu;
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

        if ( isInLimits(row-1, col) && storage[ xyTo1D(row-1,col) ] == true)  //north
            qu.union(xyTo1D(row, col), xyTo1D(row-1, col));
        if ( isInLimits(row, col+1) && storage[ xyTo1D(row, col+1)] == true)  //east
            qu.union(xyTo1D(row, col), xyTo1D(row, col+1));
        if ( isInLimits(row+1, col) && storage[ xyTo1D(row+1, col)] == true)  //south
            qu.union(xyTo1D(row, col), xyTo1D(row+1, col));
        if ( isInLimits(row, col-1) && storage[ xyTo1D(row, col-1)] == true)  //west
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

        //TODO
        //qu.connected(xyTo1D(row, col), )???
        // is it connected
       // qu.connected()
        return true;
    }

    // does the system percolate?
    // if we fill all open sites connected to the top row and that process fills some open site on the bottom row.
    public boolean percolates() {

        //TODO

        return true;
    }

    private int xyTo1D(int row, int col) {

        if (!isInLimits(row, col))
            throw new IndexOutOfBoundsException("row index i out of bounds");

        return ((gridSize * row) + col) - gridSize ;
    }

    private boolean isInLimits(int row, int col) {

        return ((row <= 0 || row > gridSize) || (col <= 0 || col > gridSize));
    }


    public static void main(String[] args) {


        //Percolation test = new Percolation(5);

        //test

        // Define grid
        // open some sites

        // open(1, 1) and open(1, 2), and then to ensure that the two corresponding
        // entries are connected (using .connected() in WeightedQuickUnionUF).


    }
}