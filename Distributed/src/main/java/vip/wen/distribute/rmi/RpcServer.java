package vip.wen.distribute.rmi;

import vip.wen.distribute.rmi.anno.RpcAnnotation;
import vip.wen.distribute.rmi.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private IRegisterCenter registerCenter;//注册中心
    private String serviceAddress;//服务发布地址

    //存放服务名称和服务对象之间的关系
    private Map<String,Object> handlerMap = new HashMap<>();

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    /**
     * 绑定服务名称和服务对象
     * @param services
     */
    public void bind(Object... services){
        for (Object service:services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            String version = annotation.version();
            if (version!=null && !version.equals("")){
                serviceName=serviceName+"-"+version;
            }
            handlerMap.put(serviceName,service);//绑定服务接口名称对应的服务
        }
    }

    public void publisher(){
        ServerSocket serverSocket = null;
        try {
            String[] addrs = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(addrs[1]));

            for (String interfaceName:handlerMap.keySet()) {
                registerCenter.register(interfaceName,serviceAddress);
                System.out.println("注册服务成功： "+interfaceName+"->"+serviceAddress);

            }


            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
