package vip.wen.distribute.rmi.client;

import vip.wen.distribute.rmi.RemoteInvocationHandle;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    /**
     *
     * @param interfaceCls
     * @param <T>
     * @return
     */
    public <T>T clentProxy(final Class<T> interfaceCls,String version){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandle(this.serviceDiscovery,version));
    }

    /**
     * 创建客户端的远程连接，通过远程代理进行访问
     * @param interfaceCls
     * @param host
     * @param port
     * @param <T>
     * @return
     *//*
    public <T>T clentProxy(final Class<T> interfaceCls,final String host,final int port){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandle(host,port));
    }*/

}
