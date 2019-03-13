package vip.wen.pattern.singleton.tcase;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import vip.wen.pattern.singleton.demo.LazySimpleSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class LazySimpleSingletonTest {

    /*简单多线程测试
    public static void main(String[] args){
        try {
            ExecutorThread t1 = new ExecutorThread();
            ExecutorThread t2 = new ExecutorThread();
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            th1.start();
            th2.start();
        }catch (Exception e){
            e.printStackTrace();

        }
    }*/

    /**
     * 反射破坏Simple懒加载单例
     * @param args
     */
    public static void main(String[] args){
        try{
            LazySimpleSingleton simple1 = LazySimpleSingleton.getInstance();
            Class clazz = LazySimpleSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            LazySimpleSingleton simple2 = (LazySimpleSingleton) constructor.newInstance();
            System.out.println("simple1:"+simple1);
            System.out.println("simple2:"+simple2);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
