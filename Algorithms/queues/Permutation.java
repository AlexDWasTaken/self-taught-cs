import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 1) {
            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            int n = Integer.parseInt(args[0]);
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
                queue.enqueue(s);
            }
            for (int i = 0; i < n; i++) {
                StdOut.println(queue.dequeue());
            }
        }
    }
}