package com.marxicb.advancedclass.a03generics;

public class Test {
    public static void main(String[] args) {
//        MyArrayList<String> arrayList;
//        arrayList = new MyArrayList<>();
//        arrayList.add("123");
//        System.out.println(arrayList.get(0));
        MyArrayList<?>[] list=new MyArrayList<?>[]{new MyArrayList<Integer>(),new MyArrayList<String>()};
    }
}
