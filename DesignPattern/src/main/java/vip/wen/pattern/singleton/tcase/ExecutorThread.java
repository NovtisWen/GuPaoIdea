package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazySimpleSingleton;

public class ExecutorThread implements Runnable{

    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();

        System.out.println(Thread.currentThread().getName()+"："+singleton);
    }
}
