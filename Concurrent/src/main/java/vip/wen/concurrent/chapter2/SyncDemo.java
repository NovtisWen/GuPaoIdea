package vip.wen.concurrent.chapter2;

import java.awt.*;

public class SyncDemo {

    Object lock;

    public SyncDemo(Object lock) {
        this.lock = lock;
    }

    public synchronized void demo(){//对象锁

    }

    public void demo2(){
        //TODO

        synchronized (this){
            //代码块锁，保护存在线程安全的变量
        }
    }

    public synchronized static void demo3(){

    }

    public void demo4(){
        //作用域是类级别的，全局的
        synchronized (SyncDemo.class){

        }
    }

    public void demo5(){
        //TODO
        //锁是存在lock对象上的
        synchronized (lock){
            //代码块锁，保护存在线程安全的变量
        }
    }

    public static void main(String[] args){
        Object lock = new Object();
        SyncDemo sd = new SyncDemo(lock);
        SyncDemo sd2 = new SyncDemo(lock);
        //此时锁是存储在lock对象上的，所以是线程安全的
        new Thread(()->sd.demo5()).start();
        new Thread(()->sd2.demo5()).start();


    }
}
