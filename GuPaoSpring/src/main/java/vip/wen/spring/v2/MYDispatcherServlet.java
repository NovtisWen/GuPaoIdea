package vip.wen.spring.v2;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MYDispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String,Object> ioc = new HashMap<String,Object>();

    private List<Handler> handlerMapping = new ArrayList<Handler>();


    @Override
    public void init(ServletConfig config) throws ServletException {

        //1、读取配置文件
        doLoandConfig(config.getInitParameter("ContextConfigLocation"));
        //2、扫描包类
        doScanner(contextConfig.getProperty("scanPackege"));
        //3、初始化类实例，并放入IOC容器中
        doInstance();
        //4、进行DI依赖注入，对注解@Autowized，赋值给予对应参数
        doAutowired();
        //5、初始化HandleMapping，扫描IOC容器中类的方法属性，对注解项@RequestMapping类中的url和method进行一对一匹配，并存放到HandleMapping中
        initHandleMapping();

    }

    private void initHandleMapping() {
        if(!ioc.isEmpty()){return;}

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(GPController.class)){ continue;}

            //保存在类上面的URL
            String baseUrl = "";
            if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = clazz.getDeclaredMethods();
            for (int i=0;i<methods.length;i++){
                Method method = methods[i];
                if(!method.isAnnotationPresent(GPRequestMapping.class)){continue;}

                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);

                String regex = ("/"+baseUrl+requestMapping.value().replaceAll("/+","/"));
                Pattern pattern = Pattern.compile(regex);
                handlerMapping.add(new Handler(method,pattern,entry.getValue()));
                //baseUrl = (baseUrl + "/"+ requestMapping.value()).replaceAll("/","/");
                //this.handlerMapping.add(new Handler(baseUrl,method,entry.getValue()));
                System.out.println("mapping "+regex+","+method);
            }
        }


    }

    /**
     * DI依赖注入
     */
    private void doAutowired() {
        if(!ioc.isEmpty()){return ;}

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class clazz = (Class) ioc.get(entry);
            //获取该类下声明的所有的参数
            Field[] fields = clazz.getDeclaredFields();
            for (int i=0;i<fields.length;i++){
                Field field = fields[i];
                if(!field.isAnnotationPresent(GPAutowired.class)){ continue;}

                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                //如果用户没有自定义beanName，默认就根据类型注入
                //这个地方省去了对类名首字母小写的情况的判断
                String beanName = autowired.value();
                if("".equals(beanName)){
                    //获得接口的类型，作为key待会拿这个key到ioc容器中去取值
                    beanName = field.getType().getName();
                }

                field.setAccessible(true);
                if(ioc.get(beanName)!=null){
                    try {
                        field.set(entry.getValue(),ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty()){return;}
        try {
            for (String className: classNames) {
                if(!ioc.containsKey(className)){
                    Class clazz = Class.forName(className);
                    //什么样的类才需要初始化? 加了注解的类
                    //加了注解的类，才初始化，怎么判断？
                    //为了简化代码逻辑，主要体现设计思想，只举例@Controller和@Service，
                    //@Component ..就不一一举例
                    //默认类名首字母小写
                    if(clazz.isAnnotationPresent(GPController.class)){
                        Object instacne = clazz.newInstance();
                        String beanName = toLowerFirstCase(className);
                        ioc.put(beanName,instacne);
                    }else if(clazz.isAnnotationPresent(GPService.class)){
                        //1、自定义bean
                        GPService service = (GPService) clazz.getAnnotation(GPService.class);
                        String beanName= service.value();
                        //2、默认首字母小写
                        if("".equals(beanName)){
                            beanName = toLowerFirstCase(className);
                        }
                        Object instance = clazz.newInstance();
                        ioc.put(beanName,instance);

                        //3、根据类型自动赋值
                        for (Class clazzA : clazz.getInterfaces()) {
                            if(ioc.containsKey(clazzA.getName())){
                                throw new Exception("The "+clazzA.getName() +" is exists!!");
                            }
                            ioc.put(clazzA.getName(),instance);
                        }
                    }else{
                        continue;
                    }
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    private String toLowerFirstCase(String className) {
        char[] chars = className.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }

    private void doLoandConfig(String contextConfigLocation) {
        InputStream fis = null;
        fis = this.getClass().getResourceAsStream(contextConfigLocation);
        //获取到文件后将其缓存到properties中
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //将对应的包路径转化为文件路径
    private void doScanner(String scanPackege) {
        try {
            URL url = this.getClass().getClassLoader().getResource(scanPackege.replaceAll("\\.","/"));
            File classFile = new File(url.getFile());
            for (File file : classFile.listFiles()) {
                if(file.isDirectory()){
                    doScanner(scanPackege+file.getName());
                }else{
                    String className = scanPackege+"."+file.getName();
                    classNames.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.运行阶段
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail : "+Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 执行处理逻辑
     * @param req
     * @param resp
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp){
        try {
            //1、获取请求uri获取到对应方法层的RequestMapping
            String requestUrl = req.getRequestURI();
            //2、获取到顶层的Controller的RequestMapping
            String contextPath = req.getContextPath();
            requestUrl = requestUrl.replaceAll(contextPath, "").replaceAll("/", "/");
            //3、从IOC容器匹配对应的Controller、method
            Handler handler = getHandler(req);
            if (handler == null) {
                resp.getWriter().write("404 NOT FOUNd");
            }
            //4、注入方法参数
            Class<?>[] paramTypes = handler.getMethod().getParameterTypes();

            Object[] paramValues = new Object[paramTypes.length];

            Map<String,String[]> params = req.getParameterMap();
            for (Map.Entry<String, String[]> param : params.entrySet()) {


            }


            //5、执行方法返回
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Handler getHandler(HttpServletRequest req) {

        //1、获取请求uri获取到对应方法层的RequestMapping
        String url = req.getRequestURI();
        //2、获取到顶层的Controller的RequestMapping
        String contextPath = req.getContextPath();

        //String methodUrl = requestUrl.replaceAll(contextPath,"").replaceAll("/","/");
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for (Handler handler: handlerMapping) {
            Matcher matcher = handler.pattern.matcher(url);
            if (!matcher.matches()){continue;}

            return handler;
        }
        return null;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private class Handler {
        private Method method;
        private Pattern pattern;
        private Object controller;
        //参数列表
        //把参数名和参数顺序记录
        private Map<String,Integer> paramIndexMapping;

        public Method getMethod() {
            return method;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Object getController() {
            return controller;
        }

        public Handler(Method method, Pattern pattern,Object controller) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;
            paramIndexMapping = new HashMap<String, Integer>();
            putParamIndexMapping(method);
        }

        private void putParamIndexMapping(Method method) {
           //通过注解去做
           Annotation[][] paramAnnotations = method.getParameterAnnotations();
           for (int i=0;i<paramAnnotations.length;i++){
               for (Annotation a:paramAnnotations[i]) {
                   if(a instanceof GPRequestParam){
                       String typeName = ((GPRequestParam) a).value();
                        paramIndexMapping.put(typeName,i);
                   }
               }
           }
           //提取方法中的HttpRequestServlet和HttpResponseServlet
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i=0;i<parameterTypes.length;i++) {
                Class<?> type = parameterTypes[i];
                if(type == HttpServletRequest.class || type == HttpServletResponse.class){
                    paramIndexMapping.put(type.getName(),i);
                }
            }
        }
    }
}
