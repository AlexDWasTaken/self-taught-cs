/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;

    private Node last;

    private int size = 0;

    public Deque() {
        this.first = null;
        this.last = null;
    }

    private class Node {
        public Item item;

        public Node next = null;

        public Node previous = null;

        public Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Node newFirst = new Node(item, first, null);

        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
            size++;
            return;
        }

        if (item == null) {
            throw new IllegalArgumentException();
        }

        first.previous = newFirst;
        first = newFirst;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        Node newLast = new Node(item, null, last);
        if (isEmpty()) {
            first = newLast;
            last = newLast;
            size++;
            return;
        }

        if (item == null) {
            throw new IllegalArgumentException();
        }

        last.next = newLast;
        last = newLast;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        Node newFirst = first.next;

        if (newFirst == null) {
            Item frontItem = first.item;
            first = null;
            last = null;
            return frontItem;
        }

        newFirst.previous = null;
        Item frontItem = first.item;
        first = newFirst;
        return frontItem;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        Node newLast = last.previous;

        if (newLast == null) {
            Item backItem = last.item;
            first = null;
            last = null;
            return backItem;
        }

        newLast.next = null;
        Item backItem = last.item;
        last = newLast;
        return backItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addLast(7);
        for (Integer item : deque) {
            System.out.println(item);
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }
}
