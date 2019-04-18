package vip.wen.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConnectionDemo {

    public static void main(String[] args){
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("192.168.79.129:2181,192.168.79.1:2181,192.168.79.131:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected==event.getState()){
                        //如果连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();

            System.out.println(zooKeeper.getState());

            zooKeeper.create("/zk-persis-wen","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL.PERSISTENT);
            Thread.sleep(1000);

            Stat stat = new Stat();
            //得到当前节点的值
            byte[] bytes = zooKeeper.getData("/zk-persis-wen",null,stat);
            System.out.println(bytes);
            //修改节点值
            zooKeeper.setData("/zk-persis-wen","1".getBytes(),stat.getVersion());

            //得到当前节点的值
            byte[] bytes1 = zooKeeper.getData("/zk-persis-wen",null,stat);
            System.out.println(bytes1);

            zooKeeper.delete("/zk-persis-wen",stat.getVersion());

            zooKeeper.close();
            //阻塞当前线程
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
