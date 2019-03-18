package vip.wen.pattern.proxy.dcproxy;

import java.lang.reflect.Method;

public class JDKThird implements JDKInvocationHandler {

    private Object obj;

    public Object getInstance(Object person){
        this.obj = person;
        Class clazz = obj.getClass();
        return JDKProxy.newProxyInstance(new JDKClassLoader(),clazz.getInterfaces(),this);
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
