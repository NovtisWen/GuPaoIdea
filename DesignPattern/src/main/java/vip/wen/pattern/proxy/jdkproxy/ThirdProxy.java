package vip.wen.pattern.proxy.jdkproxy;

import vip.wen.pattern.proxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 第三方平台
 */
public class ThirdProxy implements InvocationHandler{

    private Object obj;

    public Object getInstance(Object person){
        this.obj = person;
        Class clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.obj,args);
        after();
        return null;
    }

    public void before(){
        System.out.println("匹配对应职业");
    }

    public void after(){
        System.out.println("找到对应工作了");
    }


}
