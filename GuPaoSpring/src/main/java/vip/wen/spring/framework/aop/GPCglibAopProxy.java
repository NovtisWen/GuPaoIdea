package vip.wen.spring.framework.aop;

import vip.wen.spring.framework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GPCglibAopProxy implements GPAopProxy,InvocationHandler {

    private GPAdvisedSupport advised;

    public GPCglibAopProxy(GPAdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader loader) {
        return null;
    }
}
