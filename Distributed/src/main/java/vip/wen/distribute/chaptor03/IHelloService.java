package vip.wen.distribute.chaptor03;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHelloService extends Remote {

    String sayHello(String msg) throws RemoteException;
}
