package by.dm13y.study;

import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface Sorter<E> {
    List<E> sort(List<E> list, Comparator<E> comparator);
}
