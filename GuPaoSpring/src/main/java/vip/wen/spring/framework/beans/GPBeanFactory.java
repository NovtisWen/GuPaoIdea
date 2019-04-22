package vip.wen.spring.framework.beans;

public interface GPBeanFactory {

    /**
     * 根据BeanName从IOC容器中获得一个实例的Bean（单例的，全局的获取点）
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 根据类获取实例
     * @param beanClass
     * @return
     * @throws Exception
     */
    Object getBean(Class<?> beanClass) throws Exception;


}
