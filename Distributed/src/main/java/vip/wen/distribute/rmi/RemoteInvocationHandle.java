package vip.wen.distribute.rmi;

import vip.wen.distribute.rmi.client.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandle implements InvocationHandler {
    private IServiceDiscovery serviceDiscovery;
    private String version;

    public RemoteInvocationHandle(IServiceDiscovery serviceDiscovery,String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    /* private String host;
    private int port;

    public RemoteInvocationHandle(String host, int port) {
        this.host = host;
        this.port = port;
    }*/

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);

        TCPTransport tcpTransport = new TCPTransport(this.host,this.port);

        return tcpTransport.send(request);*/

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(version);

        //根据接口名称得到对应的服务地址
        String serviceAddress = serviceDiscovery.discover(request.getClassName());
        TCPTransport tcpTransport = new TCPTransport(serviceAddress);
        return tcpTransport.send(request);
    }
}
