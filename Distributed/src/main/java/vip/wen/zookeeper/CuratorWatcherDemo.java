package vip.wen.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorWatcherDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString("192.168.79.129:2181,192.168.79.1:2181,192.168.79.131:2181").
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();

        curatorFramework.start();
        System.in.read();
    }

    /**
     * PathChildCache 监听一个节点下节点的创建、删除、更新
     * NodeCache 监听一个节点的更新和创建事件
     * TreeCache 综合PatchChildCache和NodeCache的特性
     */
    public static void addListenerWithNodeCache(CuratorFramework curatorFramework,String path){



    }
}
