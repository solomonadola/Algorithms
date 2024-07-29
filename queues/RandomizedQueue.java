import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    private final Random random;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
        random = new Random();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (size == queue.length) resize(2 * queue.length);
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        int index = random.nextInt(size);
        Item item = queue[index];
        queue[index] = queue[--size];
        queue[size] = null; // avoid loitering
        if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        return queue[random.nextInt(size)];
    }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledQueue;
        private int current;

        public RandomizedQueueIterator() {
            shuffledQueue = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                shuffledQueue[i] = queue[i];
            }
            shuffle(shuffledQueue);
            current = 0;
        }

        private void shuffle(Item[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                Item temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }

        public boolean hasNext() {
            return current < shuffledQueue.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffledQueue[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("RandomizedQueue is empty: " + rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println("Sampled item: " + rq.sample());
        System.out.println("Dequeued item: " + rq.dequeue());
        System.out.println("RandomizedQueue size after dequeue: " + rq.size());
    }
}
