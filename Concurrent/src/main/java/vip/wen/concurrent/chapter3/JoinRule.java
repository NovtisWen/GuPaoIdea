package vip.wen.concurrent.chapter3;

public class JoinRule {

    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
       /* Thread t1 = new Thread(()->{
            x=100;
        });
        x=10;
        t1.start();
        System.out.println(x);
        t1.join();
        System.out.println(x);*/
        //t1.start();
        Thread t1 = new Thread(()->{
            System.out.println("t1");
        });
        Thread t2 = new Thread(()->{
            System.out.println("t2");
        });
        Thread t3 = new Thread(()->{
            System.out.println("t3");
        });
        t1.start();
        t1.join();//阻塞主线程，等待子线程完成
        //等待阻塞释放
        t2.start();
        t2.join();
        t3.start();
    }

}
