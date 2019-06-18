package vip.wen.concurrent.chapter4;

public class App {
    public synchronized void demo(){//main获得对线锁
        System.out.println("demo");
        demo2();
    }

    private void demo2() {
        synchronized (this){//增加重入次数就行
            System.out.println("demo2");
        }//释放的时候，减少重入次数
    }

    public static void main(String[] args){
        App app=new App();
        app.demo();
    }

}
