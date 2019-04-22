package vip.wen.spring.framework.aop.support;

import vip.wen.spring.framework.aop.aspect.GPAfterReturningAdviceInteceptor;
import vip.wen.spring.framework.aop.aspect.GPAfterThrowingAdviceInterceptor;
import vip.wen.spring.framework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import vip.wen.spring.framework.aop.config.GPAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private GPAopConfig config;

    private Pattern pointCutClassPattern;

    private Map<Method,List<Object>> methodCache;

    //解析配置
    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    //获取目标类
    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public Object getTarget(){
        return null;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cached = methodCache.get(method);
        if (cached == null){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            //cached = methodCache.get(m);
            this.methodCache.put(m,cached);
        }
        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    /**
     * 解析配置config
     */
    private void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);
        pointCutClassPattern = Pattern.compile("class "+ pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ")+1));

        try {
            methodCache = new HashMap<>();

            Pattern pattern = Pattern.compile(pointCut);

            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String,Method> aspectMethods = new HashMap<String, Method>();
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(),method);
            }


            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()){
                    //执行器链
                    List<Object> advices = new LinkedList<Object>();
                    //把每一个方法包装成MethodIterceptor
                    //before
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))){
                        //创建Advice对象
                        //GPAdvice
                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                    }
                    //after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))){
                        //创建Advice对象
                        //GPAdvice
                        advices.add(new GPAfterReturningAdviceInteceptor(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                    }
                    //afterThrowing
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))){
                        //创建Advice对象
                        //GPAdvice
                        GPAfterThrowingAdviceInterceptor throwingAdvice = new GPAfterThrowingAdviceInterceptor(
                                aspectMethods.get(config.getAspectAfterThrow()),
                                aspectClass.newInstance());
                        throwingAdvice.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(m, advices);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTarget(Object instance) {

    }

    /**
     * 判断是不是代理类
     * @return
     */
    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
