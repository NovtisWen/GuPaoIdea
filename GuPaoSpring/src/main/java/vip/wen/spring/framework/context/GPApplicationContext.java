package vip.wen.spring.framework.context;

import vip.wen.spring.framework.aop.GPAopProxy;
import vip.wen.spring.framework.aop.GPCglibAopProxy;
import vip.wen.spring.framework.aop.GPJdkDynamicAopProxy;
import vip.wen.spring.framework.aop.config.GPAopConfig;
import vip.wen.spring.framework.aop.support.GPAdvisedSupport;
import vip.wen.spring.service.GPService;
import vip.wen.spring.annotation.GPAutowired;
import vip.wen.spring.controller.GPController;
import vip.wen.spring.framework.beans.GPBeanFactory;
import vip.wen.spring.framework.beans.config.GPBeanDefinition;
import vip.wen.spring.framework.beans.support.GPBeanDefinitionReader;
import vip.wen.spring.framework.beans.support.GPBeanWrapper;
import vip.wen.spring.framework.beans.support.GPDefaultListableBeanFactory;
import vip.wen.spring.framework.config.GPBeanPostProcessor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 按照源码分析的流程，IOC、DI、MVC、AOP
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader;
    //单例的IOC容器的缓存
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    //通用的IOC容器，存储所有的
    private Map<String,GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, GPBeanWrapper>();

    //ClassPathXmlA
    public GPApplicationContext(String... configLocations){
        this.configLocations=configLocations;
    }

    @Override
    public void refresh() {
        //1、定位、定位配置文件（各种类型的加载方式，应该使用策略模式）
        reader = new GPBeanDefinitionReader(this.configLocations);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，配置信息放到容器里面(伪IOC容器，目前只保存了配置信息)
        doRegiserBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，提前初始化
        doAutoWrited();
    }

    //只处理非延迟加载的情况
    private void doAutoWrited() {
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegiserBeanDefinition(List<GPBeanDefinition> beanDefinitions) {
        for (GPBeanDefinition beanDefinition:beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    public Object getBean(Class<?> beanClass) throws Exception{
        return getBean(beanClass.getName());
    }

    @Override
    public Object getBean(String beanName) throws Exception{

        GPBeanDefinition gpBeanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        //这个逻辑还不严谨，自己可以去参考Spring源码
        //初始化通知器，应当用工厂模式+策略模式
        GPBeanPostProcessor processor = new GPBeanPostProcessor();// before、after

        //通知之前
        processor.postProcessBeforeInitialization(instance,beanName);

        //1、初始化
        //gpBeanDefinition.setFactoryBeanName(beanName);
        instance = instantiateBean(beanName,gpBeanDefinition);
        //之所以分成两步操作，是因为有循环注入的情况
        //class A{B b;}
        //class B{A a;}
        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

        GPBeanWrapper gpBeanWrapper = new GPBeanWrapper(instance);

        //创建一个代理的策略，看是用CGLib用JDK
        /*GPAopProxy proxy;
        Object proxy = proxy.getProxy();
        createProxy();*/

        //之所以分成两步操作，是因为有循环注入的情况
        //class A{B b;}
        //class B{A a;}
        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

        //2、拿到BeanWrapper之后，把BeanWrapper保存到IOC容器中
/*        if(this.factoryBeanInstanceCache.containsKey(beanName)){
            throw new Exception("The "+beanName+" is exits!");
        }*/
        this.factoryBeanInstanceCache.put(beanName,gpBeanWrapper);

        //通知之后
        processor.postProcessAfterInitialization(instance,beanName);
        //3、赋值注入 循环注入
        populateBean(beanName, new GPBeanDefinition(), gpBeanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, GPBeanDefinition gpBeanDefinition, GPBeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();

        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        //判断，只有加了注解的类，才执行依赖注入
        if(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class)){
            return;
        }

        //获取所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            if(!field.isAnnotationPresent(GPAutowired.class)){continue;
            }
            GPAutowired autowired = field.getAnnotation(GPAutowired.class);

            String autowirezBeanName = autowired.value().trim();
            if ("".equals(autowirezBeanName)){
                autowirezBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                //为什么会为Null
                if(this.factoryBeanInstanceCache.get(autowirezBeanName) == null){continue;}
                field.set(instance,this.factoryBeanInstanceCache.get(autowirezBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }

    private Object instantiateBean(String beanName, GPBeanDefinition gpBeanDefinition) {
        //1、拿到需要实例化的对象的类名
        String className = gpBeanDefinition.getBeanClassName();
        //2、反射实例化，得到对象
        Object instance = null;
        try {
            //gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例，细节暂且忽略，先完成主线
            if(this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                GPAdvisedSupport config = instantionAopConfig(gpBeanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //判断符合PointCut的规则话，将创建代理对象，并覆盖instance
                if(config.pointCutMatch()){
                    instance = createProxy(config).getProxy();
                }

                //Class clazz = Class.forName(className);
                //instance = clazz.newInstance();
                this.singletonObjects.put(className,instance);
                //再加一个，可根据beanDefinition获取实例
                this.singletonObjects.put(gpBeanDefinition.getFactoryBeanName(),instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //3、将对象分装到BeanWrapper中
        //GPBeanWrapper beanWrapper = new GPBeanWrapper(instance);

        //singletonObjects


        //factoryBeanInstanceCache


        //4、吧BeanWrapper存入到真正的IOC容器里面

        return instance;
    }

    private GPAopProxy createProxy(GPAdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length > 0){
            return new GPJdkDynamicAopProxy(config);
        }
        return new GPCglibAopProxy(config);
    }

    private GPAdvisedSupport instantionAopConfig(GPBeanDefinition gpBeanDefinition) {
        GPAopConfig config = new GPAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new GPAdvisedSupport(config);
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
