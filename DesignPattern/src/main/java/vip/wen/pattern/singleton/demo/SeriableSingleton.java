package vip.wen.pattern.singleton.demo;

import java.io.Serializable;

/**
 * 饿汉式单例
 */
public class SeriableSingleton implements Serializable {

    private final static SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton(){

    }

    public synchronized static SeriableSingleton getInstance(){
        return INSTANCE;
    }

    /**
     * 反序列化破坏单例方法
     * FileInputStream的readObject()方法会通过反射调用对象的readSolve()方法，这里直接返回了本身实例，避免了序列化破坏
     * @return
     */
    public Object readSolve(){
        return INSTANCE;
    }

}
