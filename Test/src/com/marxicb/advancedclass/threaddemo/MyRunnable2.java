package com.marxicb.advancedclass.threaddemo;

public class MyRunnable2 implements Runnable{
    int ticket=0;
    public void run(){
        while(true){
            if(method()) break;
        }
    }
    private synchronized boolean method(){
        if(ticket==100){
            return true;
        }else{
            try {
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            ticket++;
            System.out.println(Thread.currentThread().getName()+"正在卖第"+ticket+"张票!!！");
            return false;
        }
    }
}