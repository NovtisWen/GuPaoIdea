package vip.wen.concurrent.chapter6;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    static int count;

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    static void incr() throws InterruptedException {

        count++;
        Thread.sleep(1);
    }

    private AtomicInteger aa;

    public static void main(String[] args){
        for (int i=0;i<1000;i++){
            //new Thread(AtomicDemo::incr).start();
        }

        Integer a = new Integer(1);

        AtomicInteger atomicInteger = new AtomicInteger(1);


    }
}
