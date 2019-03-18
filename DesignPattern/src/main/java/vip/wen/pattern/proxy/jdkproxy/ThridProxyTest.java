package vip.wen.pattern.proxy.jdkproxy;

import vip.wen.pattern.proxy.Person;

import java.lang.reflect.Method;

public class ThridProxyTest {


    public static void main(String[] args) throws Exception{
        Person soft = (Person)new ThirdProxy().getInstance(new SoftEngineer());
        soft.findJob();
        //soft.study();
        System.out.println("=================");
        Object obj = new ThirdProxy().getInstance(new Lawyer());
        Method method = obj.getClass().getDeclaredMethod("findJob");
        method.invoke(obj);
    }
}
