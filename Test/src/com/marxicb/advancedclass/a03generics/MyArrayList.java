package com.marxicb.advancedclass.a03generics;

public class MyArrayList<E> {
    Object[] obj = new Object[10];
    int size = 0;

    public boolean add(E e) {
        obj[size++] = e;
        return true;
    }
    public E get(int index){
        if(index < 0 || index >= size) return null;
        return (E) obj[index];
    }
}
