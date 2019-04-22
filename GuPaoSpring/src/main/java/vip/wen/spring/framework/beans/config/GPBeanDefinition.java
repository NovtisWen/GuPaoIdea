package vip.wen.spring.framework.beans.config;

import lombok.Data;

/**
 * 存储Bean的注册信息
 */
@Data
public class GPBeanDefinition {

    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
    private boolean isSingleton = true;
}
