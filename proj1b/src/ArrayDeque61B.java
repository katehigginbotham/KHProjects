import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;
    private int pos;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = items.length - 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = get(i);
        }
        items = a;
        first = capacity;
        last = size;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        } else if (items == null) {
            first = 0;
            items[first] = x;
            size += 1;
        }
        first = Math.floorMod(first - 1, items.length);
        items[first] = x;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        } else if (items == null) {
            last = 0;
            items[last] = x;
            size += 1;
        }
        last = Math.floorMod(last + 1, items.length);
        items[last] = x;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
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
        if (items == null) {
            return null;
        }
        T returnItem = get(0);
        items[Math.floorMod(first, items.length)] = null;
        size -= 1;
        first = Math.floorMod(first, items.length);
        first += 1;
        if (size > 0 & size == items.length / 4) {
            resize(items.length / 2);
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (items == null) {
            return null;
        }
        T returnItem = get(size - 1);
        items[size - 1] = null;
        size -= 1;
        last -= 1;
        last = Math.floorMod(last, items.length);
        if (size > 0 & size == items.length / 4) {
            resize(items.length / 2);
        }
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 | index >= items.length) {
            return null;
        }
        return items[Math.floorMod(first + index, items.length)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public class ArrayDequeIterator implements Iterator<T> {
        public ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = get(pos);
            pos += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ArrayDeque61B otherArrayDeque) {
            if (this.size != otherArrayDeque.size) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (!this.get(i).equals(otherArrayDeque.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (T item: this) {
            sb.append(item.toString());
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
