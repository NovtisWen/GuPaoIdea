package vip.wen.pattern.singleton.demo;

import vip.wen.pattern.singleton.tcase.ExecutorThread;

public class LazySimpleSingleton {

    private static LazySimpleSingleton lazySimpleSingleton = null;

    private LazySimpleSingleton(){}

    public synchronized static LazySimpleSingleton getInstance() {
        if (lazySimpleSingleton == null){
            lazySimpleSingleton = new LazySimpleSingleton();
        }
        return lazySimpleSingleton;
    }

}
