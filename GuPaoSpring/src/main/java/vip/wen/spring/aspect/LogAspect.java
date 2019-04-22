package vip.wen.spring.aspect;

public class LogAspect {

    public void before(){
         //往对象里面记录调用的开始时间
    }

    public void after(){
        //系统当前时间-之前记录的开始的时间=方法调用消耗的时候
        //能够检测出方法的性能
    }

    public void afterThrowing(){
        //异常检测，可以拿到异常的信息
    }
}
