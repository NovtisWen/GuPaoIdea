package vip.wen.spring.framework.webmvc.servlet;

import lombok.extern.slf4j.Slf4j;
import vip.wen.spring.annotation.GPRequestMapping;
import vip.wen.spring.controller.GPController;
import vip.wen.spring.framework.context.GPApplicationContext;
import vip.wen.spring.v2.MYDispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class GPDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    private List<GPHandlerMapping> handlerMappings = new ArrayList<GPHandlerMapping>();

    private Map<GPHandlerMapping,GPHandlerAdaptor> handlerAdapters = new ConcurrentHashMap<GPHandlerMapping,GPHandlerAdaptor>();

    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"+ Arrays.toString(e.getStackTrace()));
            e.printStackTrace();;
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandleMapping
        GPHandlerMapping handler = getHandler(req);

        if(handler == null){
            processDispatcherResult(req,resp,new GPModelAndView("404"));
            return;
        }

        //2、准备好调用前的参数，将获得的handle处理成可用的参数
        GPHandlerAdaptor adaptor = getHandleAdapter(handler);

        //3、真正的调用方法，存储了返回的ModleAndView存储了要传到页面上的值和页面模板的名称
        GPModelAndView mv = adaptor.handle(req,resp,handler);

        //4、真正的输出
        processDispatcherResult(req, resp, mv);
    }

    private void processDispatcherResult(HttpServletRequest req, HttpServletResponse resp, GPModelAndView mv) throws Exception {
        //把给我的ModleAndView变成一个HTML、OutputStream、Json、freemarker等
        //ContextType
        if (null == mv){return;}

        //如果ModelAndView不为Null，对其进行渲染
        if(this.viewResolvers.isEmpty()){return ; }

        for (GPViewResolver viewResolver : this.viewResolvers) {
            //获得View
            GPView view =viewResolver.resolveViewName(mv.getViewName(), null);
            //将数据Model传入到View中
            view.render(mv.getModel(), req, resp);
        }

    }

    private GPHandlerAdaptor getHandleAdapter(GPHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return null;
        }
        GPHandlerAdaptor ha = this.handlerAdapters.get(handler);
        if (ha.supports(handler)) {
            return ha;
        }
        return null;
    }

    private GPHandlerMapping getHandler(HttpServletRequest req) throws Exception {

        //1、获取请求uri获取到对应方法层的RequestMapping
        String url = req.getRequestURI();
        //2、获取到顶层的Controller的RequestMapping
        String contextPath = req.getContextPath();

        //String methodUrl = requestUrl.replaceAll(contextPath,"").replaceAll("/","/");
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for (GPHandlerMapping handler:this.handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()){continue;}
            return handler;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化ApplicationContext
        context = new GPApplicationContext(CONTEXT_CONFIG_LOCATION);
        //2、初始化Spring MVC九大组件
        initStrategies(context);

    }


    //初始化策略
    protected void initStrategies(GPApplicationContext context) {
        //多文件上傳組件
        initMultipartResolver(context);
        //国际化组件
        initLocaleResolver(context);
        //主题
        initThemeResolver(context);
        //handldeMapping，必须实现
        initHandlerMappings(context);

        //参数适配组件，必须实现
        initHandlerAdapters(context);

        //异常处理组件
        initHandlerExceptionResolvers(context);

        //请求与视图名称装换
        initRequestToViewNameTranslator(context);

        //初始化试图处理组件，必须实现，每一个view都需要配置一个viewResolvers
        initViewResolvers(context);

        //管理参数中转（缓冲器）
        initFlashMapManager(context);
    }

    private void initFlashMapManager(GPApplicationContext context) {

    }

    /**
     * 把获得文件转换成可以输出的html、json等
     * @param context
     */
    private void initViewResolvers(GPApplicationContext context) {

        //拿到一个模板的存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        for (File template : templateRootDir.listFiles()) {
            //
            this.viewResolvers.add(new GPViewResolver(templateRoot));
        }

    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {

    }

    private void initHandlerAdapters(GPApplicationContext context) {
        //把一个request请求，封装成一个Handle，参数都是字符串，自动匹配到handler中的方法的形参

        //可想而知，它要拿到HandlerMapping才能开始干活
        //意味着，有几个HandlerMapping，就有几个HandlerAdapter
        for (GPHandlerMapping hanldeMapping: this.handlerMappings) {
            this.handlerAdapters.put(hanldeMapping,new GPHandlerAdaptor());
        }
    }

    private void initHandlerMappings(GPApplicationContext context) {

        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();

                if(!clazz.isAnnotationPresent(GPController.class)){continue;}
                //保存在类上面的URL
                String baseUrl = "";
                if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                    GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getDeclaredMethods();
                for (int i=0;i<methods.length;i++){
                    Method method = methods[i];
                    //判断没有加RequestMapping注解的直接忽略
                    if(!method.isAnnotationPresent(GPRequestMapping.class)){continue;}

                    GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);

                    String regex = ("/"+baseUrl+"/"+requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(regex);

                    this.handlerMappings.add(new GPHandlerMapping(pattern, controller, method));
                    //baseUrl = (baseUrl + "/"+ requestMapping.value()).replaceAll("/","/");
                    //this.handlerMapping.add(new Handler(baseUrl,method,entry.getValue()));
                    //log.debug("mapping "+regex+","+method);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initThemeResolver(GPApplicationContext context) {

    }

    private void initLocaleResolver(GPApplicationContext context) {

    }

    private void initMultipartResolver(GPApplicationContext context) {


    }

}
