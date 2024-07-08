package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.ArrayBlockingQueue;

public class Foodie extends Thread{
    ArrayBlockingQueue<String> queue;
    public Foodie(ArrayBlockingQueue<String> queue){this.queue=queue;};
    public void run(){
        while(true){
            try{
                String food=queue.take();
                System.out.println(food);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
