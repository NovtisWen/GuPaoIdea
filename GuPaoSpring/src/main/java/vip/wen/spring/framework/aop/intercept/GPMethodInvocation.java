package vip.wen.spring.framework.aop.intercept;

import vip.wen.spring.framework.aop.aspect.GPJointPoint;

import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拦截器链，责任链模式
 */
public class GPMethodInvocation implements GPJointPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;

    //存储自定义属性
    private Map<String,Object> userAttributes;

    //定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;

    public GPMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;

    }

    //执行的逻辑
    public Object proceed() throws Throwable {
        //如果Interceptor执行完了，则执行joinPoint，如果没有
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(target,arguments);
        }

        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof GPMethodInterceptor) {
            // Evaluate dynamic method matcher here: static part will already have
            // been evaluated and found to match.

            GPMethodInterceptor mi =
                    (GPMethodInterceptor) interceptorOrInterceptionAdvice;
            //动态匹配：运行时参数是否满足匹配条件
            return mi.invoke(this);

        } else {
            //执行当前Intercetpor
            return proceed();
        }
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (userAttributes != null){
            if (!userAttributes.containsKey(key)){return;}
            userAttributes.put(key,value);
        }else{
            userAttributes = new HashMap<>();
            userAttributes.put(key,value);
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return userAttributes.get(key);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }
}
