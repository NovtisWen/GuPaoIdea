package vip.wen.pattern.singleton.tcase;

import sun.applet.Main;
import vip.wen.pattern.singleton.demo.HungrySingleton;
import vip.wen.pattern.singleton.demo.LazyInnerSingleton;
import vip.wen.pattern.singleton.demo.LazySimpleSingleton;

import java.lang.reflect.Constructor;

public class ExecutorThread implements Runnable{

    @Override
    public void run() {
        HungrySingleton singleton = HungrySingleton.getInstance();

        System.out.println(Thread.currentThread().getName()+"："+singleton);
    }

    /*  饿汉式单例线程安全
        public static void main(String[] args){
        Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        t1.start();
        t2.start();
    }*/

    /**
     * 通过反射的方式可以破坏饿汉式单例
     * @param args
     */
    public static void main(String[] args){
        try{
            HungrySingleton singleton1 = HungrySingleton.getInstance();
            Class clazz = HungrySingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            HungrySingleton singleton2 = (HungrySingleton) constructor.newInstance();

            System.out.println("simple1:"+singleton1);
            System.out.println("simple2:"+singleton2);
            System.out.println("两个实例是否相同："+(singleton1==singleton2));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
