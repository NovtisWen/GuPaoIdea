package vip.wen.spring.framework.aop;

import vip.wen.spring.framework.aop.intercept.GPMethodInvocation;
import vip.wen.spring.framework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 核心类
 *  通过JDK的方式获取一个代理对象
 */
public class GPJdkDynamicAopProxy implements GPAopProxy,InvocationHandler {

  private GPAdvisedSupport advised;

  public GPJdkDynamicAopProxy(GPAdvisedSupport conifg) {
    this.advised = conifg;
  }

  @Override
  public Object getProxy() {
    return getProxy(this.advised.getTargetClass().getClassLoader());
  }

  @Override
  public Object getProxy(ClassLoader classLoader) {
    /**
     * 通过字节码重组，生成一个全新的类，代替原生的类，并通过增强的功能实现
     */
    return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
  }

  /**
   * 而Spring为了更好的管理、分离、封装，则将增强的方法都封装到了一个Interceptor拦截器执行链中，
   * 通过加载配置，将配置拦截的方法进行解析，最终为每一个方法生成一个拦截器链条，定义了执行顺序
   * @param proxy
   * @param method
   * @param args
   * @return
   * @throws Throwable
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //根据方法获取该方法的执行 链，上下文（before、after、after Throwing等）
    List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
    GPMethodInvocation invocation = new GPMethodInvocation(proxy,this.advised.getTargetClass(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
    return invocation.proceed();
  }


}
