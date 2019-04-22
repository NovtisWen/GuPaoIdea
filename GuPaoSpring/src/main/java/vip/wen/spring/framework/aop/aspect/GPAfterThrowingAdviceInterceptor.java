package vip.wen.spring.framework.aop.aspect;

import vip.wen.spring.framework.aop.intercept.GPMethodInterceptor;
import vip.wen.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPAfterThrowingAdviceInterceptor  extends GPAbstractAspectAdvice implements GPMethodInterceptor {

    private String throwName;

    public GPAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Exception e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwName = throwName;
    }
}
