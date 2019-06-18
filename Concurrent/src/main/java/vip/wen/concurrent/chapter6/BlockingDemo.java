package vip.wen.concurrent.chapter6;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingDemo {

    //阻塞队列
    ArrayBlockingQueue<String> ab = new ArrayBlockingQueue(10);
    {
        init();
    }

    public void init(){
        new Thread(()->{
            while (true) {
                try {
                    String data = ab.take();//阻塞方式获得元素
                    System.out.println("receive:" + data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addData(String data) throws InterruptedException {
        ab.add(data);
        System.out.println("sendData:"+data);
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingDemo blockingDemo = new BlockingDemo();
        blockingDemo.init();
        for (int i=0;i<1000;i++){
            blockingDemo.addData("data:"+i);
        }
    }
}
