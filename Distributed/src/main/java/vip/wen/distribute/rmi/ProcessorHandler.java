package vip.wen.distribute.rmi;

import com.sun.corba.se.impl.ior.ObjectAdapterIdNumber;
import sun.misc.Version;
import sun.security.provider.certpath.Vertex;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Map<String,Object> handleMap;

    public ProcessorHandler(Socket socket, Map<String,Object> handleMap){
        this.socket=socket;
        this.handleMap=handleMap;;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream =null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());

            RpcRequest request = (RpcRequest) inputStream.readObject();
            Object result = invoke(request);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object[] args = request.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++){
            types[i] = args[i].getClass();
        }
        String serviceName = request.getClassName();
        String version = request.getVersion();
        if (version!=null&&!version.equals("")){
            serviceName = serviceName+"-"+version;
        }
        //从handlerMap中，根据客户端请求的地址，去拿到响应的服务，通过反射发起调用
        Object service = handleMap.get(request.getClassName());
        //旧方法
        Method method = service.getClass().getMethod(request.getMethodName(),types);
        return method.invoke(service,args);
    }
}
