package collection;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomArrayList<T> extends AbstractList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(Collection<? extends T> collection) {
        elements = new Object[Math.max(collection.size(), DEFAULT_CAPACITY)];
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @Override
    public boolean add(T element) {
        growIfNeeded();
        elements[size++] = element;
        modCount++;
        return true;
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T old = get(index);
        elements[index] = element;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed = get(index);
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        modCount++;
        return removed;
    }

    private void growIfNeeded() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
