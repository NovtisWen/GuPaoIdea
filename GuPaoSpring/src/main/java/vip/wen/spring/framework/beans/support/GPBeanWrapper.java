package vip.wen.spring.framework.beans.support;

/**
 * 保存期模式
 */
public class GPBeanWrapper {
    //实例化的类
    private Object wrappedInstance;
    //也保存了Class对象
    private Class<?> wrappedClass;

    public GPBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    /**
     * 如果是单列，则从Wrapped里面拿出来
     * Return the bean instance wrapped by this object.
     */
    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    /**
     * 如果不是单例，则直接new一个返回回去
     * Return the type of the wrapped bean instance.
     */
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }


}
