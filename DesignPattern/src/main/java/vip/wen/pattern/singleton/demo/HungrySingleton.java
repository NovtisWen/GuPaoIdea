package vip.wen.pattern.singleton.demo;

/**
 * 饿汉单例
 */
public class HungrySingleton {

    private static final HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton(){

    }

    public static HungrySingleton getInstance(){
        return singleton;
    }

}
