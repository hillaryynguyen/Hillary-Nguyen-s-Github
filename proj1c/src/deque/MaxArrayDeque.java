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
    public T max() {
        if (isEmpty()) {
            return null;
        }

        T maxElement = getFirst();

        for (T element : this) {
            if (comparator.compare(element, maxElement) > 0) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        T maxElement = getFirst();

        for (T element : this) {
            if (c.compare(element, maxElement) > 0) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }

        if (index < 0) {
            index += size();
        }

        int currentIndex = 0;
        for (T element : this) {
            if (currentIndex == index) {
                return element;
            }
            currentIndex++;
        }
        return null;
    }

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
            T item = (T) toArray()[currentIndex];
            currentIndex++;
            return item;
        }
    }
}
