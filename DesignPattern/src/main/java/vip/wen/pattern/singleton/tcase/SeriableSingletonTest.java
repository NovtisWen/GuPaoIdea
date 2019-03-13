package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazyInnerSingleton;
import vip.wen.pattern.singleton.demo.SeriableSingleton;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * 饿汉式序列单例
 */
public class SeriableSingletonTest {

    /*简单多线程测试
    public static void main(String[] args){

        SeriableThread s1 = new SeriableThread();
        SeriableThread s2 = new SeriableThread();
        Thread t1= new Thread(s1);
        Thread t2 = new Thread(s2);
        t1.start();
        t2.start();
    }

    static class SeriableThread implements Runnable{

        @Override
        public void run() {
            SeriableSingleton singleton = SeriableSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"： "+singleton);
        }
    }*/

    /**
     * 反射破坏 序列化单例
     * @param args
     */
    /*public static void main(String[] args){
        try{
            SeriableSingleton singleton1 = SeriableSingleton.getInstance();
            Class clazz = SeriableSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            SeriableSingleton singleton2 = (SeriableSingleton) constructor.newInstance();
            SeriableSingleton singleton3 = (SeriableSingleton) constructor.newInstance();
            System.out.println("simple1:"+singleton1);
            System.out.println("simple2:"+singleton2);
            System.out.println("simple3:"+singleton3);

        }catch (Exception e){
            e.printStackTrace();
        }

    }*/

    /**
     * 序列化方式破坏 序列单例
     * 现将对象实例写入IO字节流中
     * 再用IO流读取该字节反序列化成实例
     * 但生成的实例对象已不相同
     * @param args
     */
    public static void main(String[] args){
        SeriableSingleton singleton1 = SeriableSingleton.getInstance();
        SeriableSingleton singleton2 = null;
        try{
           FileOutputStream fos = new FileOutputStream("SeriableSingleton.obj");
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(singleton1);
           oos.flush();
           oos.close();

           FileInputStream  fis = new FileInputStream("SeriableSingleton.obj");
           ObjectInputStream ois = new ObjectInputStream(fis);
           singleton2 = (SeriableSingleton) ois.readObject();
           System.out.println("singleton1:"+singleton1);
           System.out.println("singleton2:"+singleton2);
           System.out.println("两个实例是否一致："+(singleton1==singleton2));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
