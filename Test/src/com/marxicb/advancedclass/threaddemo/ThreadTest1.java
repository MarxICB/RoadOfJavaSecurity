package com.marxicb.advancedclass.threaddemo;

public class ThreadTest1 {
    public static void main(String[] args) {
        Mythread t1=new Mythread();
        Mythread t2=new Mythread();
        Mythread t3=new Mythread();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
