package vip.wen.spring.framework.aop;

/**
 * 获得一个代理对象
 */
public interface GPAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader loader);

}
