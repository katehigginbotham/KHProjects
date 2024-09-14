package deque;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public Node sentinel;
    public int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public class Node {
        public Node prev;
        public T item;
        public Node next;
        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void addFirst(T x) {
        Node first = new Node(null, x, null);
        first.next = sentinel.next;
        first.prev = sentinel;
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node last = new Node(null, x, null);
        last.prev = sentinel.prev;
        last.next = sentinel;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (isEmpty()) {
            return returnList;
        }
        for (Node curr = sentinel.next; curr != null; curr = curr.next) {
            returnList.add(curr.item);
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
        Node first = sentinel.next;
        if (size == 0) {
            return null;
        }
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size -= 1;
        return first.item;
    }

    @Override
    public T removeLast() {
        Node last = sentinel.prev;
        if (size == 0) {
            return null;
        }
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size -= 1;
        return last.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        Node curr = sentinel.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    @Override
    public T getRecursive(int index) {
        Node curr = sentinel.next;
        if (index < 0 || index >= size()) {
            return null;
        } else if (index == 0) {
            return curr.item;
        }
        sentinel.next = sentinel.next.next;
        return getRecursive(index - 1);
    }
}
