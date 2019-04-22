package vip.wen.spring.framework.aop.aspect;

import vip.wen.spring.framework.aop.intercept.GPMethodInterceptor;
import vip.wen.spring.framework.aop.intercept.GPMethodInvocation;

import java.io.ObjectOutputStream;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class GPMethodBeforeAdviceInterceptor extends GPAbstractAspectAdvice implements GPMethodInterceptor {

    private GPJointPoint jointPoint;

    public GPMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method, Object[] args, Object target) throws Throwable{
        //传递给织入的参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.jointPoint,null,null);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.jointPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return null;
    }
}
