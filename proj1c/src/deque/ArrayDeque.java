package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T> {
    private static final int INITIAL_CAPACITY = 8;
    private int nextFirst;
    private T[] items;
    private int size;
    private int nextLast;

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int index = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[index];
            index = (index + 1) % items.length;
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            returnList.add(items[index]);
            index = (index + 1) % items.length;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int firstIndex = (nextFirst + 1) % items.length;
        T removedItem = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size--;

        if (size < items.length / 4 && items.length > INITIAL_CAPACITY) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int lastIndex = (nextLast - 1 + items.length) % items.length;
        T removedItem = items[lastIndex];
        items[lastIndex] = null;
        nextLast = lastIndex;
        size--;

        if (size < items.length / 4 && items.length > INITIAL_CAPACITY) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int startIndex = (nextFirst + 1) % items.length;
        int itemIndex = (startIndex + index) % items.length;
        return items[itemIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    // Implement the iterator() method
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex;

        public ArrayDequeIterator() {
            currentIndex = 0;
        }

        public boolean hasNext() {
            return currentIndex < size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = items[(nextFirst + 1 + currentIndex) % items.length];
            currentIndex++;
            return item;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same object reference, they are equal
        }

        if (!(obj instanceof ArrayDeque)) {
            return false; // Not an instance of ArrayDeque, cannot be equal
        }

        ArrayDeque<?> other = (ArrayDeque<?>) obj;

        if (this.size() != other.size()) {
            return false; // Different sizes, cannot be equal
        }

        Iterator<?> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();

        while (thisIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), otherIterator.next())) {
                return false; // Elements at the same position are not equal
            }
        }

        return true; // All elements are equal
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            sb.append(item);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}


