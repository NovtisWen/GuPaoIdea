package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.LazyDoubleSingleton;
import vip.wen.pattern.singleton.demo.LazySimpleSingleton;

import java.lang.reflect.Constructor;

public class LazyDoubleSingletonTest {
    /*简单多线程测试单例
    public static void main(String[] args){
        LazyDoubleSingletonTest lazy = new LazyDoubleSingletonTest();
        DoubleThread d1 = lazy.new DoubleThread();
        DoubleThread d2 = lazy.new DoubleThread();

        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);

        t1.start();
        t2.start();
    }

    class DoubleThread implements Runnable{

        @Override
        public void run() {
            LazyDoubleSingleton singleton = LazyDoubleSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"： "+singleton);
        }
    }*/

    /**
     * 反射破坏Simple懒加载单例
     * @param args
     */
    public static void main(String[] args){
        try{
            LazyDoubleSingleton simple1 = LazyDoubleSingleton.getInstance();
            Class clazz = LazyDoubleSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            LazyDoubleSingleton simple2 = (LazyDoubleSingleton) constructor.newInstance();
            System.out.println("simple1:"+simple1);
            System.out.println("simple2:"+simple2);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
