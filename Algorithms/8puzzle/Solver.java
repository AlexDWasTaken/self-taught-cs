import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final Node solutionNode;

    private static class MComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return o1.manhatten() - o2.manhatten();
        }
    }

    private class Node {
        public final Board board;
        public final Node previous;
        public final int step;
        private final int manhattenDistance;

        public Node(Node pre, Board newBoard) {
            if (pre != null) {
                previous = pre;
                this.step = pre.step + 1;
            }
            else {
                previous = null;
                step = 0;
            }
            board = newBoard;
            manhattenDistance = newBoard.manhattan();
        }

        public int manhatten() {
            return this.manhattenDistance + this.step;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> minPQ = new MinPQ<Node>(new MComparator());
        MinPQ<Node> dualPQ = new MinPQ<Node>(new MComparator());
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Node initialNode = new Node(null, initial);
        Node dualInitialNode = new Node(null, initial.twin());
        minPQ.insert(initialNode);
        dualPQ.insert(dualInitialNode);
        // System.out.println(initialNode.board);
        // System.out.println(dualInitialNode.board);
        Node root, dual;
        int sum = 0;
        do {
            sum++;
            root = minPQ.delMin();
            dual = dualPQ.delMin();

            if (root.board.isGoal() || dual.board.isGoal()) {
                break;
            }
            for (Board neighbor : root.board.neighbors()) {
                if (root.previous == null || !neighbor.equals(root.previous.board)) {
                    minPQ.insert(new Node(root, neighbor));
                }
            }
            for (Board neighbor : dual.board.neighbors()) {
                if (dual.previous == null || !neighbor.equals(dual.previous.board)) {
                    dualPQ.insert(new Node(dual, neighbor));
                }
            }
        } while (!minPQ.isEmpty());

        if (root.board.isGoal()) {
            solutionNode = root;
            return;
        }

        solutionNode = null;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solutionNode == null) {
            return -1;
        }
        return solutionNode.step;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> boardStack = new Stack<Board>();
        Node node = solutionNode;
        while (node != null) {
            boardStack.push(node.board);
            node = node.previous;
        }

        return boardStack;
    }

    // test client (see below) 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}