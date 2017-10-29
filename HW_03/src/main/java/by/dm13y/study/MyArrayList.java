package by.dm13y.study;

import java.util.*;

public class MyArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private int listSize;

    private Object[] elements;

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elements = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public MyArrayList(){
        elements = new Object[DEFAULT_CAPACITY];
    }

    private void grow(int minCapacity) {
        int currentCapacity = elements.length;
        int newCapacity = currentCapacity + (currentCapacity >> 1);
        if(newCapacity < 0) newCapacity = Integer.MAX_VALUE;

        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void ensureCapacityInternal(int minCapacity) {
        if(minCapacity < 0) throw new OutOfMemoryError();
        if(elements.length < minCapacity){
            grow(minCapacity);
        }
    }

    private void fastRemove(int index) {
        int numMoved = listSize - index - 1;
        if (numMoved > 0)
            System.arraycopy(elements, index+1, elements, index,
                    numMoved);
        elements[--listSize] = null; // clear to let GC do its work
    }

    private void rangeCheck(int index) {
        if (index > listSize || index < 0)
            throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, listSize);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < listSize)
            return (T[]) Arrays.copyOf(elements, listSize, a.getClass());
        System.arraycopy(elements, 0, a, 0, listSize);
        if (a.length > listSize)
            a[listSize] = null;
        return a;
    }

    @Override
    public boolean add(E element) {
        ensureCapacityInternal(listSize + 1);  // Increments modCount!!
        elements[listSize++] = element;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < listSize; index++)
                if (elements[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < listSize; index++)
                if (o.equals(elements[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> coll) {
        Object[] addedArray = coll.toArray();
        int addedSize = coll.size();
        ensureCapacityInternal(listSize + addedSize);
        System.arraycopy(addedArray, 0, elements, listSize, addedSize);
        listSize += addedSize;
        return addedSize != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> coll) {
        rangeCheck(index);

        Object[] addedArray = coll.toArray();
        int addedSize = addedArray.length;
        ensureCapacityInternal(listSize + addedSize);

        int numMoved = listSize - index;
        if (numMoved > 0)
            System.arraycopy(elements, index, elements, index + addedSize,
                    numMoved);

        System.arraycopy(addedArray, 0, elements, index, addedSize);
        listSize += addedSize;
        return addedSize != 0;
    }

    private boolean updateColl(Collection<?> coll, boolean remDubl) {
        for(Object obj : coll){
            if (obj == null) throw new NullPointerException();
        }
        boolean isUpdated = false;
        int writeIdx = 0;
        for (int curIdx = 0; curIdx < listSize; curIdx++) {
            if(coll.contains(elements[curIdx]) != remDubl){
                elements[writeIdx] = elements[curIdx];
                writeIdx++;
                isUpdated = true;
            }
        }
        listSize = writeIdx;
        return isUpdated;
    }

    @Override
    public boolean removeAll(Collection<?> coll) {
        return updateColl(coll, true);
    }

    @Override
    public boolean retainAll(Collection<?> coll) {
        return updateColl(coll, false);
    }

    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
        listSize = 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) elements[index];
    }

    @Override
    public E set(int index, E e) {
        rangeCheck(index);
        elements[index] = e;
        E oldValue = (E) elements[index];
        elements[index] = e;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheck(index);
        ensureCapacityInternal(listSize + 1);
        int newIndex = index + 1;
        System.arraycopy(elements, index, elements, newIndex, listSize - index);
        elements[index] = element;
        listSize++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E result = (E) elements[index];
        fastRemove(index);
        return result;
    }

    @Override
    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < listSize; i++)
                if (elements[i]==null)
                    return i;
        } else {
            for (int i = 0; i < listSize; i++)
                if (object.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = listSize-1; i >= 0; i--)
                if (elements[i]==null)
                    return i;
        } else {
            for (int i = listSize-1; i >= 0; i--)
                if (o.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        rangeCheck(fromIndex);
        rangeCheck(toIndex);
        List<E> subList = null;
        try {
            subList = this.getClass().newInstance();
            for(int i = fromIndex; i <= toIndex; i++) {
                subList.add(get(i));
            }
        }catch (Exception ex){

        }
        return subList;
    }

    private class MyIterator implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != listSize;
        }

        @Override
        public E next() {
            int i = cursor;
            if (i >= listSize) throw new NoSuchElementException();
            Object[] elementData = elements;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();

            MyArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    private class MyListItr extends MyIterator implements ListIterator<E> {
        MyListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = elements;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
