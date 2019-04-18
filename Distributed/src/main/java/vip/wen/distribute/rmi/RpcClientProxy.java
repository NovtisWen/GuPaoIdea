package vip.wen.distribute.rmi;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public <T>T clentProxy(final Class<T> interfaceCls,final String host,final int port){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandle(host,port));
    }

}
