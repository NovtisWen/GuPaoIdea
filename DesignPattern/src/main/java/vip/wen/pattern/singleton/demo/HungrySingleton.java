package vip.wen.pattern.singleton.demo;

/**
 * 饿汉单例
 * 缺点：在类加载时，即会创建资源占用内存空间，会存在空间浪费
 * 优点：是线程安全的
 */
public class HungrySingleton {

    private static final HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton(){

    }

    public static HungrySingleton getInstance(){
        return singleton;
    }

}
