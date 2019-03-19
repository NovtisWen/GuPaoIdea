package vip.wen.pattern.proxy.jdkproxy;

import sun.misc.ProxyGenerator;
import vip.wen.pattern.proxy.Person;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

public class ThridProxyTest {


    public static void main(String[] args) throws Exception{
        Person soft = (Person)new ThirdProxy().getInstance(new SoftEngineer());
        soft.findJob();

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
        FileOutputStream fos = new FileOutputStream("H://$Proxy0.class");
        fos.write(bytes);
        fos.close();

        //soft.study();
        System.out.println("=================");
        Object obj = new ThirdProxy().getInstance(new Lawyer());
        Method method = obj.getClass().getDeclaredMethod("findJob");
        method.invoke(obj);


        //生成代理对象文件


    }
}
