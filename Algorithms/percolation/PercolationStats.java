/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] stats;

    private double confidence95 = 1.96;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        stats = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            stats[i] = simulate(n);
        }
    }

    private double simulate(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int randomRow = StdRandom.uniformInt(n) + 1;
            int randomCol = StdRandom.uniformInt(n) + 1;
            if (!percolation.isOpen(randomRow, randomCol)) {
                percolation.open(randomRow, randomCol);
            }
        }
        double openCount = percolation.numberOfOpenSites();
        return openCount / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println(
                String.format("mean                    = %.16f", percolationStats.mean()));
        System.out.println(
                String.format("stddev                  = %.16f", percolationStats.stddev()));
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " +
                                   percolationStats.confidenceHi() + "]");

    }
}
