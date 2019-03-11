package vip.wen.pattern.singleton.demo;

public class LazySimpleSingleton {

    private static LazySimpleSingleton lazySimpleSingleton = null;

    private LazySimpleSingleton(){}

    public synchronized static LazySimpleSingleton getInstance() {
        if (lazySimpleSingleton == null){
            return new LazySimpleSingleton();
        }
        return lazySimpleSingleton;
    }
}
