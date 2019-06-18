package vip.wen.distribute.rmi;

import vip.wen.distribute.rmi.zk.IRegisterCenter;
import vip.wen.distribute.rmi.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * 集群测试
 */
public class LBServerDemo1 {
    public static void main(String[] args) throws IOException {
        IGpHello iGpHello = new GpHelloImpl();
        IGpHello iGpHello1 = new GpHelloImpl2();

        IRegisterCenter registerCenter = new RegisterCenterImpl();
        RpcServer rpcServer = new RpcServer(registerCenter,"127.0.0.1:8081");
        rpcServer.bind(iGpHello,iGpHello1);
        rpcServer.publisher();
        System.in.read();
        //rpcServer.publisher(iGpHello,8888);
    }

}
