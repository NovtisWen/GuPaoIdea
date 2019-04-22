package vip.wen.spring.framework.aop.intercept;

/**
 * 顶层接口，每一个方法对象都去实现一个Invoke方法
 */
public interface GPMethodInterceptor {

    Object invoke(GPMethodInvocation invocation) throws Throwable;
}
