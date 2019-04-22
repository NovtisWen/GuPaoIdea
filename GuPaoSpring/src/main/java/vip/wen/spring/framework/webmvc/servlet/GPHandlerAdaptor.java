package vip.wen.spring.framework.webmvc.servlet;

import vip.wen.spring.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GPHandlerAdaptor {

    public final boolean supports(Object handler) {
        return (handler instanceof GPHandlerMapping);
    }

    public GPModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        GPHandlerMapping handlerMapping = (GPHandlerMapping) handler;

        //把方法的形参列表和request的参数列表所在的顺序进行一一对应
        Map<String,Integer> paramIndexMapping = new HashMap<String, Integer>();

        //通过注解给
        Annotation[][] paramAnnotations = handlerMapping.getMethod().getParameterAnnotations();
        for (int i=0;i<paramAnnotations.length;i++){
            for (Annotation a:paramAnnotations[i]) {
                if(a instanceof GPRequestParam){
                    String paramName = ((GPRequestParam) a).value();
                    if(!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
        //提取方法中的HttpRequestServlet和HttpResponseServlet
        Class<?>[] parameterTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i=0;i<parameterTypes.length;i++) {
            Class<?> type = parameterTypes[i];
            if(type == HttpServletRequest.class || type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        //4、注入方法参数
        Map<String,String[]> params = request.getParameterMap();
        //实参列表
        Object[] paramValues = new Object[parameterTypes.length];
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s",",");
            if(!paramIndexMapping.containsKey(param.getKey())){continue;}

            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringValue(value,parameterTypes[index]);
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }

        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
        if(result == null || result instanceof Void){ return null;}

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == GPModelAndView.class;
        if (isModelAndView) {
            return (GPModelAndView) result;
        }
        return null;
    }

    private Object caseStringValue(String value, Class<?> parameterType) {
        if (String.class == parameterType){
            return value;
        }
        if(Integer.class == parameterType){
            return Integer.valueOf(value);
        }else if(Double.class == parameterType){
            return Double.valueOf(value);
        }else{
            if(value != null){
                return value;
            }
            return null;
        }
    }
}
