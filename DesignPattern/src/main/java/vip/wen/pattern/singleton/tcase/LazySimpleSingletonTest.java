package vip.wen.pattern.singleton.tcase;

public class LazySimpleSingletonTest {

    public static void main(String[] args){
        try {
            ExecutorThread t1 = new ExecutorThread();
            ExecutorThread t2 = new ExecutorThread();
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            th1.start();
            th2.start();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

}
