package vip.wen.spring.framework.aop.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class GPAbstractAspectAdvice implements GPAdvice{

    private Method aspectMethod;
    private Object aspectTarget;

    public GPAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    protected Object invokeAdviceMethod(GPJointPoint jointPoint, Object returnValue, Throwable tx) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramTypes =this.aspectMethod.getParameterTypes();

        if (null == paramTypes || paramTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object[] args = new Object[paramTypes.length];
            for (int i=0;i < paramTypes.length; i++) {
                if (paramTypes[i] == GPJointPoint.class){
                    args[i] = jointPoint;
                }else if (paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }

            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
