package vip.wen.concurrent.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionNotify implements Runnable{

    private Lock lock;

    private Condition condition;


    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            lock.lock();//③、竞争锁
            System.out.println("begin - conditionNotify");
            condition.signal();//④、唤醒阻塞状态的线程
            System.out.println("end - conditionSingal");
        }finally {
            lock.unlock();//⑤、当释放锁，线程B才能再去获得锁
        }
    }
}
