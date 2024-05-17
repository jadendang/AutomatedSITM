import java.util.*;

public class CircularLinkedList<E> implements CircularLinkedListInterface<E> {
    private Node<E> front;
    public int size;

    public CircularLinkedList() {
        front = null;
        size = 0;
    }


    public Node<E> getFront() {
        return front;
    }

    public CircularLinkedList(List<E> elements) {
        front = null;
        size = 0;
        for (E element : elements) {
            add(element);
        }
    }

    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> newNode = new Node<>(value);
        if (index == 0) {
            if (front == null) {
                front = newNode;
                newNode.next = front;
            } else {
                newNode.next = front.next;
                front.next = newNode;
            }
        } else {
            Node<E> current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public void addAll(List<E> other) {
        for (E element : other) {
            add(element);
        }
    }

    public void addFront(E data) {
        add(0, data);
    }

    public int indexOf(E value) {
        Node<E> current = front;
        for (int i = 0; i < size; i++) {
            if (current.data.equals(value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    public void set(int index, E value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> current = front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = value;
    }

    public void clear() {
        front = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> current = front;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public void add(E value) {
        add(size, value);
    }

    @Override
    public boolean remove(E value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public void remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (size == 1) {
            front = null;
        } else if (position == 0) {
            Node<E> lastNode = front;
            while (lastNode.next != front) {
                lastNode = lastNode.next;
            }
            front = front.next;
            lastNode.next = front;
        } else {
            Node<E> current = front;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = front;

            @Override
            public boolean hasNext() {
                return current != null;  // Check if current node is not null (reached the end)
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

}
