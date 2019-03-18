package vip.wen.pattern.prototype.business;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtil {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void copyOf(Object source,Object target){
        try {
            Class souClazz = source.getClass();
            Field[] souFiled = souClazz.getDeclaredFields();
            souClazz.getDeclaredMethods();
            Class tarClazz = target.getClass();
            Field[] tarFiled = tarClazz.getDeclaredFields();
            for (Field sou : souFiled) {
                for (Field tar : tarFiled) {
                    if(sou.getName().equals(tar.getName())){
                        String methodName = sou.getName().substring(0, 1).toUpperCase() + sou.getName().substring(1);
                        Object souValue = souClazz.getDeclaredMethod("get"+methodName).invoke(source, null);
                        //存在Bug，如果类中引入了非基础类型的字段，即会报错
                        tarClazz.getDeclaredMethod("set"+methodName, Class.forName(tar.getGenericType().getTypeName())).invoke(target, souValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        BasePrototype prototype1 = new BasePrototype();
        prototype1.setId(1);
        prototype1.setName("wen");
        BasePrototypeVO prototypeVO = new BasePrototypeVO();
        BeanUtil.copyOf(prototype1,prototypeVO);
        System.out.println("NEWVO id："+prototypeVO.getId()+"，name："+prototypeVO.getName());

    }
}
