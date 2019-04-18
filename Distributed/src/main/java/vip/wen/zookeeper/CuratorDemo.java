package vip.wen.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString("192.168.79.129:2181,192.168.79.1:2181,192.168.79.131:2181").
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();


        curatorFramework.start();

        curatorFramework.create().creatingParentContainersIfNeeded().
                withMode(CreateMode.PERSISTENT).
                forPath("/wen/curator","1".getBytes());

        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/wen/curator");

        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/wen/curator");

        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/wen/curator","xx".getBytes());

    }
}
