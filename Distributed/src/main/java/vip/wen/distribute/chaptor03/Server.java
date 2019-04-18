package vip.wen.distribute.chaptor03;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String [] args){
        try {
            IHelloService helloService = new HelloServiceImpl();//此时，已经发布了一个远程对象
            LocateRegistry.createRegistry(1099);//服务端的启动过程
            Naming.rebind("rmi://127.0.0.1/Hello",helloService);
            System.out.println("发布启动成功");


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
