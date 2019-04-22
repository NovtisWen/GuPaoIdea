package vip.wen.spring.framework;

import vip.wen.spring.framework.context.GPApplicationContext;

public class Test {

    public static void main(String[] args){
        GPApplicationContext applicationContext = new GPApplicationContext("application.properties");
        try {
            Object e = applicationContext.getBean("myAction");
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
