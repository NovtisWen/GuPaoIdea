package vip.wen.pattern.singleton.demo;

import sun.applet.Main;

public class ThreadLocalSingleton {

    public static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance =
            new ThreadLocal<ThreadLocalSingleton>(){
                @Override
                protected ThreadLocalSingleton initialValue() {
                    return new ThreadLocalSingleton();
                }
            };

    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get();
    }

    static class LocalThread implements Runnable{
        @Override
        public void run() {
            ThreadLocalSingleton singleton = ThreadLocalSingleton.getInstance();

            System.out.println(Thread.currentThread().getName()+"："+singleton);
        }
    }

    public static void main(String[] args){
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        Thread t1 = new Thread(new LocalThread());
        Thread t2 = new Thread(new LocalThread());
        t1.start();
        t2.start();
        System.out.println("End");
    }
}
