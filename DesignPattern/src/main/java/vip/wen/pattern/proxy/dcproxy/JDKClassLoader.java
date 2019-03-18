package vip.wen.pattern.proxy.dcproxy;

import java.io.File;

/**
 * 读取生成class文件
 */
public class JDKClassLoader extends ClassLoader{

    private File classPathFile;

    public  JDKClassLoader(){
        String classPath = JDKClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {



        return super.findClass(name);
    }
}
