package vip.wen.spring.framework.beans.support;

import vip.wen.spring.framework.beans.config.GPBeanDefinition;
import vip.wen.spring.framework.context.support.GPAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {

    /** Map of bean definition objects, keyed by bean name */
    //存储注册信息的BeanDefinition，伪IOC容器
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}
