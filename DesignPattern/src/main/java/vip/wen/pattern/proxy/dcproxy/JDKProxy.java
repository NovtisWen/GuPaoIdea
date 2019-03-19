package vip.wen.pattern.proxy.dcproxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
        sb.append("packge vip.wen.pattern.proxy.dcproxy;"+LN);
        sb.append("import java.lang.reflect.*;"+LN);
        sb.append("import vip.wen.pattern.proxy.Person;"+LN);
        sb.append("public final class $Proxy0 extends Proxy implement Person{"+LN);
        sb.append("public $Proxy0(InvocationHandler invocationhandler){"+LN);
        sb.append("super(invocationhandler); }");

        Method[] methods = interfaces[0].getDeclaredMethods();
        for (Method m:methods) {
            Class<?>[] params = m.getParameterTypes();

            StringBuffer paramsName = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();

            for (int i=0;i<params.length;i++){
                Class clazz = params[i].getClass();
                String type = clazz.getName();
                String typeName = toLowerFirstCase(clazz.getSimpleName());
                paramsName.append(type+" "+typeName);
                paramValues.append(typeName);
                paramClasses.append(clazz.getName() + ".class");
                if(i < params.length-1){
                    paramsName.append(",");
                    paramClasses.append(",");
                    paramValues.append(",");
                }
            }

            sb.append("public "+m.getGenericReturnType()+" "+m.getName()+"("+paramsName+"){"+LN);
                sb.append("try{"+LN);
                    sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + LN);
                    sb.append((hasReturnValue(m.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})",m.getReturnType()) + ";" + LN);
                sb.append("}catch(Error _ex) { }"+LN);
                sb.append("catch(Throwable throwable){"+LN);
                sb.append("throw new UndeclaredThrowableException(throwable);}"+LN);
                sb.append(getReturnEmptyCode(m.getReturnType()));
            sb.append("}"+LN);

        }


        return sb.toString();
    }


    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
