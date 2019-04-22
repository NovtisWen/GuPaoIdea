package vip.wen.spring.framework.aop.aspect;

import vip.wen.spring.framework.aop.intercept.GPMethodInterceptor;
import vip.wen.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GPAfterReturningAdviceInteceptor  extends GPAbstractAspectAdvice implements GPMethodInterceptor {

    private GPJointPoint jointPoint;

    public GPAfterReturningAdviceInteceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.jointPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws InvocationTargetException, IllegalAccessException {
        super.invokeAdviceMethod(this.jointPoint,retVal,null);
    }
}

