import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private final int[][] tiles;

    private final int n;

    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private class NeighborIterator implements Iterator<Board> {

        int[][][] cache = new int[4][][];

        int current = 0;

        public NeighborIterator() {
            int pointer = 0;
            int posX = -1;
            int posY = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tiles[i][j] == 0) {
                        posX = i;
                        posY = j;
                        break;
                    }
                }
            }

            if (posX == -1) {
                throw new RuntimeException("Wrong board initialization");
            }

            // left
            if (posY >= 1) {
                int[][] tilesLeft = copyTiles();
                tilesLeft[posX][posY] = tilesLeft[posX][posY - 1];
                tilesLeft[posX][posY - 1] = 0;
                cache[pointer++] = tilesLeft;
            }

            // right
            if (posY < n - 1) {
                int[][] tilesLeft = copyTiles();
                tilesLeft[posX][posY] = tilesLeft[posX][posY + 1];
                tilesLeft[posX][posY + 1] = 0;
                cache[pointer++] = tilesLeft;
            }

            // up
            if (posX >= 1) {
                int[][] tilesLeft = copyTiles();
                tilesLeft[posX][posY] = tilesLeft[posX - 1][posY];
                tilesLeft[posX - 1][posY] = 0;
                cache[pointer++] = tilesLeft;
            }

            // down
            if (posX < n - 1) {
                int[][] tilesLeft = copyTiles();
                tilesLeft[posX][posY] = tilesLeft[posX + 1][posY];
                tilesLeft[posX + 1][posY] = 0;
                cache[pointer] = tilesLeft;
            }
        }

        private int[][] copyTiles() {
            int[][] res = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    res[i][j] = tiles[i][j];
                }
            }
            return res;
        }

        public boolean hasNext() {
            return current < 4 && cache[current] != null;
        }

        public Board next() {
            return new Board(cache[current++]);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        this.tiles = newTiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append("\n");
        for (int[] row : tiles) {
            for (int item : row) {
                stringBuilder.append(String.format("%6d", item));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != (i * n + j + 1) % (n * n)) {
                    if (tiles[i][j] != 0) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    // sum += n - row + n - col;
                    continue;
                }
                int colOriginal = (tiles[row][col] - 1) % n;
                int rowOriginal = (tiles[row][col] - 1) / n;
                sum += Math.abs(colOriginal - col) + Math.abs(rowOriginal - row);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (!((row * n + col + 1) % (n * n) == tiles[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        if (((Board) y).dimension() != this.dimension()) {
            return false;
        }

        if (Arrays.deepEquals(tiles, ((Board) y).tiles)) {
            return true;
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborIterable();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int tmpi = -1, tmpj = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (tmpi == -1) {
                    tmpi = i;
                    tmpj = j;
                }
                else {
                    int[][] newtiles = arrayCopy(tiles, n);
                    int tmp = newtiles[i][j];
                    newtiles[i][j] = newtiles[tmpi][tmpj];
                    newtiles[tmpi][tmpj] = tmp;
                    return new Board(newtiles);
                }
            }
        }
        throw new RuntimeException("Unable to find a twin board.");
    }

    private int[][] arrayCopy(int[][] a, int n) {
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] original = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        int[][] another = {
                { 8, 0, 3 },
                { 4, 1, 2 },
                { 7, 6, 5 }
        };
        Board testBoard = new Board(original);
        Board anotherTestBoard = new Board(another);
        System.out.println(testBoard.toString());
        System.out.println(testBoard.equals(anotherTestBoard));
        System.out.println("hamming: " + testBoard.hamming());
        System.out.println("manhatton: " + testBoard.manhattan());
        System.out.println("dim: " + testBoard.dimension());
        System.out.println("twin: " + testBoard.twin().toString());
        for (Board board : anotherTestBoard.neighbors()) {
            System.out.println(board);
        }
    }

}