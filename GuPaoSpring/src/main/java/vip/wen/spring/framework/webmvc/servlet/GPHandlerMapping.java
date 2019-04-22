package vip.wen.spring.framework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class GPHandlerMapping implements Comparable{

    private Method method; //保存映射的方法
    private Pattern pattern;//保存URL的正则实例
    private Object controller;//保存方法对应的实例

    public GPHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.method = method;
        this.pattern = pattern;
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
