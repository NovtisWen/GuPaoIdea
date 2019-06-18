package vip.wen.concurrent.chapter2;

public class AtomicDemo {

    private static int count =0;

    public static void inc(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<1000;i++){
            new Thread(()->AtomicDemo.inc()).start();
        }
        Thread.sleep(5000);
        System.out.println("运行效果:"+count);
    }
}
