package vip.wen.pattern.singleton.tcase;

import vip.wen.pattern.singleton.demo.EnumSingleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 枚举单例测试
 */
public class EnumSingletonTest {

    /**
     * 序列化方式破坏 序列单例
     * @param args
     */
    public static void main(String[] args){
        EnumSingleton singleton1 = EnumSingleton.getInstance();
        singleton1.setData(new Object());
        singleton1.setCode("abc");
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
           System.out.println("singleton1:"+singleton1.getData());
           System.out.println("singleton2:"+singleton2.getData());
           System.out.println("两个实例中的值是否一致："+(singleton1.getData()==singleton2.getData()));
           System.out.println("两个实例中的值是否一致："+(singleton1.getCode()==singleton2.getCode()));
           System.out.println("两个实例是否一致："+(singleton1==singleton2));

           /*EnumSingleton2 singleton3 = EnumSingleton2.getInstance();
           singleton3.setData(new Object());
           singleton3.setCode("abc");
           System.out.println("两个不同类实例的值是否相同："+(singleton1.getCode()==singleton3.getCode()));
           System.out.println("两个实例中的值是否一致："+(singleton1.getData()==singleton3.getData()));*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 反射破坏Enum单例
     * 枚举类是只会在初始化的时候会创建实例
     * @param args

    public static void main(String[] args){
        try{
            EnumSingleton simple1 = EnumSingleton.getInstance();
            simple1.setData(new Object());
            Class clazz = EnumSingleton.class;

            Constructor constructor = clazz.getDeclaredConstructor(String.class,int.class);
            constructor.setAccessible(true);
            EnumSingleton simple2 = (EnumSingleton) constructor.newInstance();
            System.out.println("simple1:"+simple1.getData());
            System.out.println("simple2:"+simple2.getData());
            System.out.println(simple1.getData()==simple2.getData());

        }catch (Exception e){
            e.printStackTrace();
        }

    }*/
}
