package vip.wen.concurrent.chapter8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorDemo implements Runnable{

        @Override
        public void run() {
                try {
                        Thread.sleep(10);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
        }

        static ExecutorService service = Executors.newFixedThreadPool(3);

     public static void main(String[] args){
             ThreadPoolExecutor threadPoolExecutor =(ThreadPoolExecutor)service;
             threadPoolExecutor.allowCoreThreadTimeOut(true);//设置可回收核心线程数
             threadPoolExecutor.prestartAllCoreThreads();//直接进行预热
             threadPoolExecutor.setMaximumPoolSize(10);//可以动态调整最大核心数
             service.shutdownNow();//立即停止
             for (int i=0;i<100;i++){

                     service.execute(new ExecutorDemo());

             }
             //service.submit();
             service.shutdown();
             service.shutdownNow();//立即停止
             //Executors.new
     }

}
