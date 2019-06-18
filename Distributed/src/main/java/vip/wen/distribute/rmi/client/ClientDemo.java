package vip.wen.distribute.rmi.client;

import vip.wen.distribute.rmi.IGpHello;
import vip.wen.distribute.rmi.client.RpcClientProxy;
import vip.wen.distribute.rmi.zk.ZkConfig;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDemo {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {

        IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(ZkConfig.CONNECTION_STR);

        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);

        for (int i=0;i<10;i++){
            IGpHello hello = rpcClientProxy.clentProxy(IGpHello.class,null);

            System.out.println(hello.sayHello("wen"));
            Thread.sleep(1000);
        }

        //IGpHello hello = rpcClientProxy.clentProxy(IGpHello.class,"localhost",8888);
       /* IGpHello hello = rpcClientProxy.clentProxy(IGpHello.class,"2.0");

        hello.sayHello("wen");*/

    }
}
