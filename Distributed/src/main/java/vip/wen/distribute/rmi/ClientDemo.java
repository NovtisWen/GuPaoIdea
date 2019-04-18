package vip.wen.distribute.rmi;

import vip.wen.distribute.chaptor03.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDemo {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();

        IGpHello hello = rpcClientProxy.clentProxy(IGpHello.class,"localhost",8888);
        hello.sayHello("wen");

    }
}
