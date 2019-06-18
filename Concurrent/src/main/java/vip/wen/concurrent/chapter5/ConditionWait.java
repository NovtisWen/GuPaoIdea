package vip.wen.concurrent.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionWait implements Runnable{

    private Lock lock;

    private Condition Condition;


    public ConditionWait(Lock lock, Condition condition) {
        this.lock = lock;
        Condition = condition;
    }

    @Override
    public void run() {

        try {

            lock.lock();//①、竞争锁
            System.out.println("begin - conditionWait");
            try {
                Condition.await();//②、阻塞
                System.out.println("end - conditionWait");//⑥、被唤醒后，获得锁继续执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();//⑦、再释放锁
        }
    }
}
