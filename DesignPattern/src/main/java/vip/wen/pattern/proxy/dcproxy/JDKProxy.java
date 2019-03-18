package vip.wen.pattern.proxy.dcproxy;

public class JDKProxy {

    public static final String LN = "\r\n";

    public static Object newProxyInstance(JDKClassLoader loader,
                                          Class<?>[] interfaces,
                                          JDKInvocationHandler h)
            throws IllegalArgumentException
    {
        //1、动态生成源码

        //2、Java文件输出到磁盘

        //3、动态编译代码，生成对应class文件

        //4、生成对应字节码文件



        return null;

    }

    private static String generateSrc(Class<?>[] interfaces){
        StringBuffer sb = new StringBuffer();
        sb.append("");

        return sb.toString();
    }
}
