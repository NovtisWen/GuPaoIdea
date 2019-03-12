package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazyDoubleSingleton;
import vip.wen.pattern.singleton.demo.LazyInnerSingleton;

public class LazyInnerSingletonTest {

    public static void main(String[] args){
        LazyInnerSingletonTest innerTest = new LazyInnerSingletonTest();
        InnerThread i1 = innerTest.new InnerThread();
        InnerThread i2 = innerTest.new InnerThread();

        Thread t1 = new Thread(i1);
        Thread t2 = new Thread(i2);
        t1.start();
        t2.start();
    }


    class InnerThread implements Runnable{

        @Override
        public void run() {
            LazyInnerSingleton singleton = LazyInnerSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"： "+singleton);
        }
    }
}
