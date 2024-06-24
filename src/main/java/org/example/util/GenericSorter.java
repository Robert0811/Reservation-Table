package org.example.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenericSorter<T> {

    public List<T> sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
        return list;
    }
}