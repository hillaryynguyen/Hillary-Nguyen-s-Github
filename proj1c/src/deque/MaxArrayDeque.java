package deque;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    // Constructor that takes a Comparator
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    // Other methods from your MaxArrayDeque class

    // Implement the iterator() method to make MaxArrayDeque iterable
    @Override
    public Iterator<T> iterator() {
        return new MaxArrayDequeIterator();
    }

    // Inner class to provide the iterator
    private class MaxArrayDequeIterator implements Iterator<T> {
        private int currentIndex;

        public MaxArrayDequeIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = (T)toArray()[currentIndex];
            currentIndex++;
            return item;
        }
    }
}
