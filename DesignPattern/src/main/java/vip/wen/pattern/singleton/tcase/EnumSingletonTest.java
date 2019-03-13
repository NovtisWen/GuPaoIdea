package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.EnumSingleton;
import vip.wen.pattern.singleton.demo.LazyDoubleSingleton;
import vip.wen.pattern.singleton.demo.SeriableSingleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * 枚举单例测试
 */
public class EnumSingletonTest {

    /**
     * 序列化方式破坏 序列单例
     * 因为枚举类覆盖了序列实例化的方法
     * @param args
     */
    /*public static void main(String[] args){
        EnumSingleton singleton1 = EnumSingleton.getInstance();
        EnumSingleton singleton2 = null;
        try{
           FileOutputStream fos = new FileOutputStream("EnumSingleton.obj");
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(singleton1);
           oos.flush();
           oos.close();

           FileInputStream  fis = new FileInputStream("EnumSingleton.obj");
           ObjectInputStream ois = new ObjectInputStream(fis);
           singleton2 = (EnumSingleton) ois.readObject();
           System.out.println("singleton1:"+singleton1);
           System.out.println("singleton2:"+singleton2);
           System.out.println("两个实例是否一致："+(singleton1==singleton2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /**
     * 反射破坏Enum单例
     * @param args
     */
    public static void main(String[] args){
        try{
            EnumSingleton simple1 = EnumSingleton.getInstance();
            Class clazz = EnumSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            EnumSingleton simple2 = (EnumSingleton) constructor.newInstance();
            System.out.println("simple1:"+simple1);
            System.out.println("simple2:"+simple2);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
