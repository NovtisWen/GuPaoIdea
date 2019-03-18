package vip.wen.pattern.proxy.dcproxy;

import java.lang.reflect.Method;

public interface JDKInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
