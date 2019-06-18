package vip.wen.concurrent.chapter2;

public class ThreadDemo {

    private static volatile ThreadDemo instance = null;

    public static ThreadDemo getInstance(){
        if(instance == null){
            instance = new ThreadDemo();//底层JVM会分为三个操作
        }
        return instance;
    }

    public static void main(String[] args){
        ThreadDemo.getInstance();
    }
}
