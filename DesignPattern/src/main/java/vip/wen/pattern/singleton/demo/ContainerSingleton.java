package vip.wen.pattern.singleton.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册式单例
 * 容器缓存写法
 */
public class ContainerSingleton {

    private ContainerSingleton(){
    }

    public static Map<String,Object> ioc = new ConcurrentHashMap<String,Object>();

    public static Object getBean(String name) {
       synchronized (ioc) {
           if (!ioc.containsKey(name)) {
               Object obj = null;
               try {
                   obj = Class.forName(name).newInstance();
                   ioc.put(name, obj);
                   return obj;
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           return ioc.get(name);
       }
    }

}
