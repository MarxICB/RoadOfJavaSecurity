package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mythread2 extends Thread{
    static int ticket=0;
    static Lock lock=new ReentrantLock();
    public void run(){
        while(true){
            lock.lock();
                //同步代码块
            try {
                if(ticket<100){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ticket++;
                    System.out.println(getName()+"正在卖第"+ticket+"张票!!！");
                }else{
                    break;
                }
            } finally {
                lock.unlock();
            }

        }
    }
}
