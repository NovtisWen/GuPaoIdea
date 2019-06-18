package vip.wen.distribute.rmi.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 分布式配置中心
 */
public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).
                build();
        curatorFramework.start();
    }

    @Override
    public void register(String serverName, String serviceAddress) {
        //注册相应的服务
        String servicePath = ZkConfig.ZK_REGISTER_PATH+"/"+serverName;
        try {
            //如果根节点不存在，则创建
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT).forPath(servicePath, "0".getBytes());
            }
            String addressPath = servicePath+"/"+serviceAddress;
            String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL).
                    forPath(addressPath,"0".getBytes());
            System.out.println("服务注册成功："+rsNode);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
