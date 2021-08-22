package arrayList;

import java.util.*;
import java.util.function.Consumer;

public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;
    public static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE;

    private T[] data;
    private int size;

    public ArrayList(){
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(final int initialCapacity){
        if(initialCapacity > 0){
            this.data = (T[]) new Object[initialCapacity];
        }
        throw new IllegalArgumentException("Illegal Capacity: "+
                initialCapacity);
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true :false;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o)== -1 ? false : true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if(a.length < size)
            return (T1[]) Arrays.copyOf(data, size, a.getClass());
        System.arraycopy(data,0,a,0,size);
        if(a.length>size){
            a[size] = null;
        }
        return a;

    }

    @Override
    public boolean add(T element) {
        ensureCapacity(size + 1);
        data[size++] = element;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            for(int index = 0; index < size; index++){
                if(data[index] == null){
                    fastRemove(index);
                    return true;
                }
            }
        }else {
            for(int index = 0; index < size; index++){
                if(o.equals(data[index])){
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int modeCount = size-1-index;
        if(modeCount > 0){
            System.arraycopy(data, index + 1, data, index, modeCount);
        }
        data[size-1] = null;
        --size;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object element : c){
            if(!contains(element))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] arrayToAdd = c.toArray();
        ensureCapacity(size + arrayToAdd.length);
        System.arraycopy(arrayToAdd, 0, data, size, arrayToAdd.length);
        size += arrayToAdd.length;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] arrayToAdd = c.toArray();
        ensureCapacity(size + arrayToAdd.length);
        System.arraycopy(data, index, data, index + arrayToAdd.length, size - index);
        System.arraycopy(arrayToAdd, 0, data, index, arrayToAdd.length);
        size += arrayToAdd.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] data = this.data;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(data[r]) == complement)
                    data[w++] = data[r];
        } finally {
            if (r != size) {
                System.arraycopy(data, r,
                        data, w,
                        size - r);
                w += size - r;
            }
            if (w != size) {
                for (int i = w; i < size; i++)
                    data[i] = null;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        for(int counter = 0; counter < size; counter++)
            data[counter] = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return data[index];
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index);
        T oldElement = data[index];
        data[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        rangeCheckForAdd(index);
        ensureCapacity(index + 1);
        data[index] = element;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        T temp = data[index];
        if(index < size - 1){
            System.arraycopy(data, index + 1, data, index, size - 1 - index);
        }
        data[--size] = null;
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        if(o == null){
            for(int counter = 0; counter < size; counter++){
                if(data[counter] == null)
                    return counter;
            }
        }
        else {
            for(int counter = 0; counter < size; counter++){
                if(o.equals(data[counter]))
                    return counter;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(o == null){
            for (int index = size-1; index >=0; index--){
                if(data[index] == null){
                    return index;
                }
            }
        }else {
            for (int index = size-1; index >=0; index--){
                if(o.equals(data[index])){
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public void ensureCapacity(int minCapacity){
        if(minCapacity > data.length){
            grow(minCapacity);
        }
    }

    private void rangeCheck(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            throw new OutOfMemoryError();
        // minCapacity is usually close to size, so this is a win:
        data = Arrays.copyOf(data, newCapacity);
    }

    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList.this.data;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size ) {
                consumer.accept((T) elementData[i++]);
            }
            cursor = i;
            lastRet = i - 1;
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) elementData[lastRet = i];
        }

        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T e) {

            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

}


