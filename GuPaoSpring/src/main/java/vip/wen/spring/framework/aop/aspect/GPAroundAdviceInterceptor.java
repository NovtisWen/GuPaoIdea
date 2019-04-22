package vip.wen.spring.framework.aop.aspect;

import vip.wen.spring.framework.aop.intercept.GPMethodInterceptor;
import vip.wen.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPAroundAdviceInterceptor extends GPAbstractAspectAdvice implements GPMethodInterceptor {

    private GPJointPoint jointPoint;

    public GPAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        System.out.println("环绕之前");
        Object retVal = mi.proceed();
        mi.getMethod().invoke(mi.getThis());
        System.out.println("环绕之后");
        return null;
    }
}
