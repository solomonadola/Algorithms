import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
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
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println("Deque is empty: " + deque.isEmpty());
        deque.addFirst(1);
        deque.addLast(2);
        System.out.println("Deque size: " + deque.size());
        System.out.println("Removed from front: " + deque.removeFirst());
        System.out.println("Removed from back: " + deque.removeLast());
    }
}
