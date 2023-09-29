package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Objects;

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
            T item = get(currentIndex);
            currentIndex++;
            return item;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayDeque secList) {
            if (this.size() != secList.size()) {
                return false;
            }
            Iterator<T> thisIterator = this.iterator();
            Iterator<T> otherIterator = secList.iterator();
            while (thisIterator.hasNext()) {
                T curr = thisIterator.next();
                T otherValue = otherIterator.next();
                if (curr != otherValue) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}


