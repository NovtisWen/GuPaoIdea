package vip.wen.pattern.singleton.demo;

public class LazyInnerSingleton {

    //依然可能会被 反射攻击
    //1、虽然构造方法私有了，但是，逃不过反射的法眼
    //2、序列化的方式可以破坏单例
    private LazyInnerSingleton(){
        //if(LazyInner.INLAZY != null) {//1、反射解决方法通过检查的方式
        // throw new RuntimeException("不准构建多个实例");
        //}
    }

    //懒汉式单例
    //LazyHolder里面的逻辑需要等到外部的方法调用时才执行
    //巧妙利用了内部类的特性
    //JVM底层执行逻辑，完美的避免了线程安全问题
    public static final LazyInnerSingleton getInstance(){
        return LazyInner.INLAZY;
    }

    private static class LazyInner{
        private static final LazyInnerSingleton INLAZY = new LazyInnerSingleton();
    }
}
