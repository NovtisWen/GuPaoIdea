package vip.wen.concurrent.chapter8;

import java.util.concurrent.*;

public class FutureDemo implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("execute:call");
        Thread.sleep(5000);
        return "Hello Call";
    }

    public static void  main(String[] args) throws ExecutionException, InterruptedException {
        FutureDemo futureDemo = new FutureDemo();
        FutureTask futureTask = new FutureTask(futureDemo);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());

        ExecutorService service = Executors.newFixedThreadPool(3);
        Future future = service.submit(futureDemo);
        System.out.println(future.get());//阻塞获取查询结果
    }
}
