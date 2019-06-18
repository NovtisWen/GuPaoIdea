package vip.wen.concurrent.chapter4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    static Lock lock = new ReentrantLock();
    //sychonized的原子操作改造成这样，


    public static void main(String[] args){
        lock.lock();    //获得一个锁
        //read：
        lock.unlock();  //释放一个锁
    }
}
