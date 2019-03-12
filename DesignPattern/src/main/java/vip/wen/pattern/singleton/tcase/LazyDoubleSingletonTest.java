package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazyDoubleSingleton;

public class LazyDoubleSingletonTest {

    public static void main(String[] args){
        LazyDoubleSingletonTest lazy = new LazyDoubleSingletonTest();
        DoubleThread d1 = lazy.new DoubleThread();
        DoubleThread d2 = lazy.new DoubleThread();

        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);

        t1.start();
        t2.start();
    }

    class DoubleThread implements Runnable{

        @Override
        public void run() {
            LazyDoubleSingleton singleton = LazyDoubleSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"： "+singleton);
        }
    }
}
