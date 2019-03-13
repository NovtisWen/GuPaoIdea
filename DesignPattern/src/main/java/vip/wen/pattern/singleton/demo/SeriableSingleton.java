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
     * 覆盖方法返回本身实例
     * @return
     */
    public Object readSolve(){
        return INSTANCE;
    }

}
