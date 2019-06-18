package vip.wen.concurrent.chapter5;

import vip.wen.concurrent.chapter2.ThreadA;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo extends Thread {

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args){
        for (int i=0;i<1000;i++){
            new CountDownLatchDemo().start();
        }
        countDownLatch.countDown();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();//阻塞 1000个线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
        System.out.println("ThreadName:"+Thread.currentThread().getName());
    }

/*public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(()->{
            System.out.println("Thread1");
            countDownLatch.countDown();
        }).start();
        new Thread(()->{
            System.out.println("Thread2");
            countDownLatch.countDown();
        }).start();
        new Thread(()->{
            System.out.println("Thread3");
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
    }*/
}
