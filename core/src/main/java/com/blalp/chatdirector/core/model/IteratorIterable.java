package com.blalp.chatdirector.core.model;

import java.util.Iterator;

public class IteratorIterable<T> implements Iterable<T>, Iterator<T> {
    Iterator<T> iterator;

    public IteratorIterable(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

}
