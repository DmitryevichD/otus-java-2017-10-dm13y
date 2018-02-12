package by.dm13y.study;

import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface Sorter<T> {
    List<T> sort(List<T> list, Comparator<T> comparator);
}
