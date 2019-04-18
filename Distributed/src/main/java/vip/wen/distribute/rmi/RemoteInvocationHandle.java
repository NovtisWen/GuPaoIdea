package vip.wen.distribute.rmi;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandle implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandle(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);

        TCPTransport tcpTransport = new TCPTransport(this.host,this.port);

        return tcpTransport.send(request);
    }
}
