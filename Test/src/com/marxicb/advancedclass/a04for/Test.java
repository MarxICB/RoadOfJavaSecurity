package com.marxicb.advancedclass.a04for;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        Collection<String> coll=new ArrayList<>();
        coll.add("zs");
        coll.add("ls");
        //匿名内部类
        coll.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //lambda表达式
        coll.forEach((String s)->{
            System.out.println(s);
        });
        coll.forEach(s->System.out.println(s));
        coll.forEach(System.out::println);
    }

}
