package vip.wen.spring.framework.beans.support;

import vip.wen.spring.framework.beans.config.GPBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {

    private Properties config = new Properties();
    //固定配置文件中的key，相当于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    private List<String> registryBeanClass = new ArrayList<String>();



    //通过URL定位找到其所对应的文件，然后转换为文件流
    public GPBeanDefinitionReader(String... locations) {
        InputStream is =  this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //扫描包类文件
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String property) {
        // URL url = this.getClass().getClassLoader().getResource(property.replaceAll("\\.","/"));
        URL url = this.getClass().getResource(property.replaceAll("\\.","/"));
        File classFile = new File(url.getFile());
        for (File file : classFile.listFiles()) {
            if(file.isDirectory()){
                doScanner(property+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){continue;}
                String className = property+"."+file.getName();
                registryBeanClass.add(className);
            }
        }

    }

    public Properties getConfig(){
        return this.config;
    }

    //配置文件中扫描到的所有的配置信息转换为GPBeanDefinition对象，以便于之操作方便
    public List<GPBeanDefinition> loadBeanDefinitions(){
        List<GPBeanDefinition> results = new ArrayList<GPBeanDefinition>();
        //封装
        try {
            for (String className :registryBeanClass){
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化
                //用它实现类进行实例化
                if(beanClass.isInterface()){continue;}
                //beanName有三种情况：
                //1、默认是类名首字母
                //2、自定义名字
                //3、接口注入
                results.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                //在此添加了两次，所以需要在initHandlerMapping中去重复
                results.add(doCreateBeanDefinition(beanClass.getName(),beanClass.getName()));
                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i:interfaces) {
                    //如果是多个实现类，只能覆盖
                    //为什么？因为Spring没那么只能，就是这么傻
                    //这个时候，可以自定义名字
                    results.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
                /*GPBeanDefinition beanDefinition = doCreateBeanDefinition(className);
                if(null == beanDefinition ){continue;}
                results.add(beanDefinition);*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }


    private GPBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        try {



        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将每一个配置信息解析成一个BeanDefinition
     * @param className
     * @return
     */
    private GPBeanDefinition doCreateBeanDefinition(String className){
        try {
            Class<?> beanClass = Class.forName(className);
            if(!beanClass.isInterface()) {
                GPBeanDefinition beanDefinition = new GPBeanDefinition();
                beanDefinition.setBeanClassName(className);
                beanDefinition.setFactoryBeanName(beanClass.getSimpleName());
                return beanDefinition;
            }
            //有可能是一个接口，用它的实现类作为beanClassName
            //if(beanClass.isInterface()){continue;}

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //如果类名本身是小写字母，确实会出问题
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII要小于小写字母的ASCII码
        //在JAVA中，对char做数学运算，实际上就是对ASCII码做数学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
