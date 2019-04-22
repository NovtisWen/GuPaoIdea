package vip.wen.spring.framework.aop.aspect;

import java.lang.reflect.Method;

public interface GPJointPoint {

    Object getThis();

    Method getMethod();

    Object[] getArguments();

    void setUserAttribute (String key, Object value);

    Object getUserAttribute(String key);
}
