/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int step = 0;
        private int size;
        private RandomizedQueue<Integer> recorder;
        private int[] indices;

        public RandomizedIterator(int n) {
            size = n;
            recorder = new RandomizedQueue<Integer>();
            indices = new int[n];
            for (int i = 0; i < n; i++) {
                recorder.enqueue(i);
            }
            for (int i = 0; i < n; i++) {
                indices[i] = recorder.dequeue();
            }
        }

        public boolean hasNext() {
            return step < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[indices[step++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        items[n++] = item;

        if (n == items.length) {
            Item[] newItems = (Item[]) new Object[2 * n];
            for (int i = 0; i < n; i++) {
                newItems[i] = items[i];
            }
            items = newItems;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(n);
        Item result = items[index];
        items[index] = items[--n];  // The last element
        items[n + 1] = null;
        if (n <= 0.25 * items.length) {
            Item[] newItems = (Item[]) new Object[(int) (items.length / 2)];
            for (int i = 0; i < n; i++) {
                newItems[i] = items[i];
            }
            items = newItems;
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator(n);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 1; i <= 3000; i *= 3) {
            rq.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(rq.dequeue());
        }
        System.out.println("---");
        for (int item : rq) {
            System.out.println(item);
        }
    }
}
