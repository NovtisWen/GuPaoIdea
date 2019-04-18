package vip.wen.spring.v3;

import vip.wen.spring.service.GPService;
import vip.wen.spring.annotation.GPAutowired;
import vip.wen.spring.annotation.GPRequestMapping;
import vip.wen.spring.annotation.GPRequestParam;
import vip.wen.spring.controller.GPController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class GPDispatcherServletBak extends HttpServlet {
    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    //保存扫描的所有的类名
    private List<String> classNames = new ArrayList<String>();
    //IOC容器，我们来揭开它的神秘面纱
    private Map<String,Object> ioc = new HashMap<String,Object>();
    //保存url和Method的对应关系
    private Map<String,Method> handleMapping = new HashMap<String,Method>();
    //
    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2、扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3、初始化扫描到的类，并将其放入到IOC容器中
        try {
            doInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4、依赖注入
        doAutowired();

        //5、初始化HandleMapping
        initHandleMapping();

        System.out.println("GP Spring framework is complete");
    }

    //扫描出相关的类
    private void doScanner(String scanPackage) {
        //scanPackage = vip.wen.spring，存储的是包路径
        //转换为文件路径，实际上就是把.替换成/就Ok了，用正则
        //classpath
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classpath = new File(url.getFile());
        for (File file:classpath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){continue; }
                String className = (scanPackage + "." + file.getName().replaceAll(".class",""));
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = null;
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相当于scanPackage=vip.wen.spring 从文件中保存到了内存中
        fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化url和Method的一对一对应关系
    private void initHandleMapping() {
        if(ioc.isEmpty()){return;}

        for (Map.Entry<String,Object> entry: ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(GPController.class)){return;}

            //保存在类上面的URL
            String baseUrl = "";
            if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //默认获取所有的public方法
            for (Method method:clazz.getMethods()) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)){return;}

                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);

                //
                String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/","/");
                handleMapping.put(url,method);
                System.out.println("Mapped:" +url +","+method);
            }
        }


    }

    //自动的依赖注入
    private void doAutowired() {
        if (ioc.isEmpty()){return;}

        for (Map.Entry<String, Object> entry:ioc.entrySet()) {
            //Declared 所有的，特定的 字段，包括private/protected/default
            //正常来说，普通的OOP编程只能拿到public的属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field: fields) {
                if(!field.isAnnotationPresent(GPAutowired.class)){return;}

                GPAutowired autowired = field.getAnnotation(GPAutowired.class);

                //如果用户没有自定义beanName，默认就根据类型注入
                //这个地方省去了对类名首字母小写的情况的判断
                String beanName = autowired.value().trim();
                if("".equals(beanName)){
                    //获得接口的类型，作为key待会拿这个key到ioc容器中去取值
                    beanName = field.getType().getName();
                }
                //如果是public意外的修饰符，只要加了@Autowired注解，都要强制赋值
                //反射中叫做暴力访问
                field.setAccessible(true);

                try {
                    //用反射机制动态给字段赋值
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void doInstance() throws Exception {
        //初始化，为DI(依赖注入)做准备
        if(classNames.isEmpty()){return;}

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                //什么样的类才需要初始化? 加了注解的类
                //加了注解的类，才初始化，怎么判断？
                //为了简化代码逻辑，主要体现设计思想，只举例@Controller和@Service，
                //@Component ..就不一一举例
                //默认类名首字母小写
                if(clazz.isAnnotationPresent(GPController.class)){
                    Object instance = clazz.newInstance();
                    // Spring默认类名首字母小写
                    // key className 首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(GPService.class)){
                    //1、自定义的beanName
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value();
                    //2、默认类名首字母小写
                    if("".equals(beanName)){
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);

                    //3、根据类型自动赋值，投机取巧的方式
                    for (Class<?> i: clazz.getInterfaces()) {
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The "+i.getName() +" is exists!!");
                        }
                        //把接口的类型直接当成了Key
                        ioc.put(i.getName(),instance);
                    }
                }else{
                    continue;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private void doScanner() {

    }

    private void doLoadConfig() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        //6、运行阶段
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail : "+Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        //绝对路径
        String url = req.getRequestURI();
        //处理成相路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/","/");

        if(!this.handleMapping.containsKey(url)){
            resp.getWriter().write("404 NOT Found!!!");
        }

        Method method = this.handleMapping.get(url);

        //传参暂时写死
        Map<String,String[]> params = req.getParameterMap();

        Class<?>[] parameterTypes =method.getParameterTypes();

        Object[] paramValues = new Object[parameterTypes.length];
        for (int i=0;i<parameterTypes.length;i++){
            Class parameterType = parameterTypes[i];
            //并不能用instanceod，parameterType它不是实参，而是形参
            if(parameterType == HttpServletRequest.class){
                paramValues[i] = req;
                continue;
            }else if(parameterType == HttpServletResponse.class){
                paramValues[i] = resp;
                continue;
            }else if(parameterType == String.class){
                //将方法上的注解拿到
                Annotation[][] pa = method.getParameterAnnotations();

                //循环注解数组
                for (int j=0;j<pa.length;j++){
                    for (Annotation a : pa[j]) {
                        //我们要解析的只是GPRequestParam
                        //拿到参数的名称
                        if(a instanceof GPRequestParam){
                            String paramName = ((GPRequestParam) a).value();

                            //http://localhost/demo/query?name=Tom
                            //params key="name" value=["Tom"]
                            //简化版本
                            //GPRequestParam requestParam = (GPRequestParam) parameterType.getAnnotation(GPRequestParam.class);

                            //从req拿到的参数列表中去找到对应的key
                            if(params.containsKey(paramName)){
                                //for (Map.Entry<String, String[]> param : params.entrySet()) {
                                    //拿到key所对应的值，而拿到的之歌值，是一对多的关系
                                    //对方接受的是String类型，所以把数据统一处理为string
                                    /*String value = Arrays.toString(param.getValue())
                                            .replaceAll("\\[\\]","");*/
                                            //.replaceAll("\\s",",");
                                    //类型的强制转换

                                    String value = Arrays.toString(params.get(paramName)).replaceAll("\\[|\\]","");

                                    paramValues[i] = convert(parameterType,value);

                                //}

                            }

                        }
                    }

                }

            }

        }

        //投机取巧的方式
        //可以通过翻身拿到Method所在class，拿到class之后还是拿到class的名称
        //再调用toLowerFirstCase获取beanName
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),new Object[]{req,resp,paramValues});

    }

    //url传过来的参数都是string类型的，HTTP是基于字符串的协议
    //只需要把string转换为任意类型就好
    private Object convert(Class<?> type,String value){
        if(Integer.class == type){
            return Integer.valueOf(value);
        }else if(Double.class == type){
            return Double.valueOf(value);
        }else if(String.class == type){
            return value;
        }
        //这时候可以使用策略模式
        return value;
    }

}
