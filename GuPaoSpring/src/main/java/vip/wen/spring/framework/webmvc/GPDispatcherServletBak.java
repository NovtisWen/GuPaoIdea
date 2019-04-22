package vip.wen.spring.framework.webmvc;

import vip.wen.spring.framework.context.GPApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GPDispatcherServletBak extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatcher(req,resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {

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
        //
        initHandlerMappings(context);
        //参数适配组件
        initHandlerAdapters(context);
        //异常处理组件
        initHandlerExceptionResolvers(context);
        //请求与视图名称装换
        initRequestToViewNameTranslator(context);
        //初始化试图处理组件
        initViewResolvers(context);
        //管理参数中转
        initFlashMapManager(context);
    }

    private void initFlashMapManager(GPApplicationContext context) {

    }

    private void initViewResolvers(GPApplicationContext context) {

    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {

    }

    private void initHandlerAdapters(GPApplicationContext context) {

    }

    private void initHandlerMappings(GPApplicationContext context) {

    }

    private void initThemeResolver(GPApplicationContext context) {

    }

    private void initLocaleResolver(GPApplicationContext context) {

    }

    private void initMultipartResolver(GPApplicationContext context) {


    }

}
