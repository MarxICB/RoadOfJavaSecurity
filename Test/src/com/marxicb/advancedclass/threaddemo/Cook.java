package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.ArrayBlockingQueue;

public class Cook extends Thread{
    ArrayBlockingQueue<String> queue;
    public Cook(ArrayBlockingQueue<String> queue){this.queue=queue;};
    public void run(){
        while(true){
            try{
                queue.put("面条");
                System.out.println("厨师放了一碗面");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
