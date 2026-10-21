package pl.mperor.interview.tasks.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public boolean add(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    public T getFirst() {
        if (isEmpty())
            throw new NoSuchElementException("List is empty!");
        return head.value;
    }

    public T getLast() {
        if (isEmpty())
            throw new NoSuchElementException("List is empty!");
        return tail.value;
    }

    public boolean contains(Object object) {
        for (T value : this) {
            if (Objects.equals(value, object))
                return true;
        }
        return false;
    }

    public boolean remove(Object object) {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), object)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private Node<T> previous;
            private Node<T> current;
            private Node<T> next = head;
            private boolean removable;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                previous = current;
                current = next;
                next = next.next;
                removable = true;
                return current.value;
            }

            @Override
            public void remove() {
                if (!removable)
                    throw new IllegalStateException();
                if (previous == null) {
                    head = next;
                } else {
                    previous.next = next;
                }
                if (current == tail) {
                    tail = previous;
                }
                current = previous;
                removable = false;
                size--;
            }
        };
    }

    private static class Node<T> {

        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
