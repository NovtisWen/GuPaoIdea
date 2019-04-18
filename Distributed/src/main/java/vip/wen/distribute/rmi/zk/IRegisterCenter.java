package vip.wen.distribute.rmi.zk;

public interface IRegisterCenter {

    /**
     * 注册服务名称和服务地址
     * @param serverName
     * @param serviceAddress
     */
    void register(String serverName,String serviceAddress);
}
