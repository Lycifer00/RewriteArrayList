import java.util.Arrays;
import java.util.Objects;

public class ArrayList<E> {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE-8;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
    private int size = 0;
    private Object[] elements;

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
    }

    private String outOfBoundsMessage(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private Object[] grow(int minCapacity) {
        return elements = Arrays.copyOf(elements,
                newCapacity(minCapacity));
    }

    ArrayList() {
        this.elements = EMPTY_ELEMENTDATA;
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) this.elements = new Object[initialCapacity];
        else if (initialCapacity == 0) this.elements = EMPTY_ELEMENTDATA;
        else throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);

    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        final int value;
        Object[] elementData;
        if ((value = size) == (elementData = this.elements).length)
            elementData = grow(size + 1);
        System.arraycopy(elementData, index,
                elementData, index + 1,
                size - index);
        elementData[index] = element;
        size = size + 1;
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elements == EMPTY_ELEMENTDATA) return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0) ? newCapacity : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) throw new OutOfMemoryError();
        else return minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    public E remove(int index) {
        Objects.checkIndex(index, size);
        final int newSize;
        final Object[] es = elements;
        E oldValue = (E) es[index];
        if ((newSize = size - 1) > index)
            System.arraycopy(es, index + 1, es, index, newSize - 1);
        es[size = newSize] = null;
        return oldValue;
    }

    public int size() {
        return size;
    }

    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elements = Arrays.copyOf(elements, size);
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean contains(E o) {
        return indexOf(o , 0, size) >= 0;
    }

    private int indexOf(Object o, int start, int end) {
        Object[] es = elements;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean add(E o) {
        if (size == elements.length)
            elements = grow(size+1);
        elements[size] = o;
        size = size + 1;
        return true;
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length
                && !(elements == EMPTY_ELEMENTDATA
                && minCapacity <= DEFAULT_CAPACITY)) {
            grow(minCapacity);
        }
    }

    public E get(int index) {
        return (E) elements[index];
    }

    public void clear() {
        final Object[] es = elements;
        for (int to = size, i = size = 0; i < to; i++) {
            es[i] = null;
        }
    }
}

