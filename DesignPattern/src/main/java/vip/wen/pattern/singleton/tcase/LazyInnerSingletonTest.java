package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazyDoubleSingleton;
import vip.wen.pattern.singleton.demo.LazyInnerSingleton;

import java.lang.reflect.Constructor;

public class LazyInnerSingletonTest {

    /*简单多线测试
    public static void main(String[] args){
        LazyInnerSingletonTest innerTest = new LazyInnerSingletonTest();
        InnerThread i1 = innerTest.new InnerThread();
        InnerThread i2 = innerTest.new InnerThread();

        Thread t1 = new Thread(i1);
        Thread t2 = new Thread(i2);
        t1.start();
        t2.start();
    }


    class InnerThread implements Runnable{

        @Override
        public void run() {
            LazyInnerSingleton singleton = LazyInnerSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"： "+singleton);
        }
    }*/

    /**
     * 反射破坏Simple懒加载单例
     * @param args
     */
    public static void main(String[] args){
        try{
            LazyInnerSingleton singleton1 = LazyInnerSingleton.getInstance();
            Class clazz = LazyInnerSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            LazyInnerSingleton singleton2 = (LazyInnerSingleton) constructor.newInstance();

            LazyInnerSingleton singleton3 = (LazyInnerSingleton) constructor.newInstance();
            System.out.println("simple1:"+singleton1);
            System.out.println("simple2:"+singleton2);
            System.out.println("simple3:"+singleton3);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
