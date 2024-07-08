package com.marxicb.advancedclass.threaddemo;

public class MyRunnable implements Runnable{
    int ticket=0;
    public void run(){
        while(true){
            synchronized (Mythread.class){
                //同步代码块
                if(ticket<100){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ticket++;
                    System.out.println(Thread.currentThread().getName()+"正在卖第"+ticket+"张票!!！");
                }else{
                    break;
                }
            }
        }
    }
}