package vip.wen.distribute.rmi;

import org.omg.PortableInterceptor.Interceptor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPTransport {

    private String serviceAddress;
    private String host;
    private int port;

    public TCPTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public TCPTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Socket newSocket(){
        System.out.println("创建一个新连接");
        Socket socket;
        try {
            String[] arrs =serviceAddress.split(":");
            socket = new Socket(arrs[0],Integer.parseInt(arrs[1]));
            return socket;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object send(RpcRequest request){
        Socket socket =null;
        try {
            socket = new Socket();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object result =inputStream.readObject();
            inputStream.close();
            outputStream.close();
            return result;
        }catch (Exception e){
            throw new RuntimeException("发起远程调用异常:"+e);
        }finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
