package vip.wen.pattern.singleton.demo;

public class LazyInnerSingleton {

    private static LazyInnerSingleton singleton = null;

    private LazyInnerSingleton(){}

    public static LazyInnerSingleton getInstance(){
        return LazyInner.inner;
    }

    static class LazyInner{
        public static LazyInnerSingleton inner = new LazyInnerSingleton();
    }
}
