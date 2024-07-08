package com.marxicb.advancedclass.a02innerclassdemo;

public class Outer {
    private int a=10;
    class Inner{
        private int a=10;
        public void show(){
            int a=30;
            System.out.println(Outer.this.a);
            System.out.println(this.a);
            System.out.println(a);
        }
    }
}
