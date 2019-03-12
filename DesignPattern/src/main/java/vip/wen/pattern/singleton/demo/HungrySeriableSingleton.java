package vip.wen.pattern.singleton.demo;

/**
 * 饿汉式单例
 */
public class HungrySeriableSingleton {

    private final static HungrySeriableSingleton INSTANCE = new HungrySeriableSingleton();

    private HungrySeriableSingleton(){

    }

    public synchronized static HungrySeriableSingleton getInstance(){
        return INSTANCE;
    }

    /**
     * 反序列化破坏单例方法
     * 覆盖方法返回本身实例
     * @return
     */
    public Object readSolve(){
        return INSTANCE;
    }

}
