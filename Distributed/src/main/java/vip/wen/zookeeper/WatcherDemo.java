package vip.wen.zookeeper;

import javafx.scene.chart.StackedAreaChart;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class WatcherDemo {

    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ZooKeeper zooKeeper = new ZooKeeper("192.168.79.129:2181,192.168.79.1:2181,192.168.79.131:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("全局的默认事件:"+event.getType());
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //如果连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();

            zooKeeper.create("/zk-persis-wen","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL.PERSISTENT);

            //通过exists绑定事件
            Stat stat = zooKeeper.exists("/zk-persis-wen", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println(event.getType()+"->"+event.getPath());
                    try {
                        //再一次去绑定事件
                        zooKeeper.exists(event.getPath(),true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            zooKeeper.setData("/zk-persis-wen","2".getBytes(),stat.getVersion());
            Thread.sleep(1000);

            zooKeeper.delete("/zk-persis-wen",stat.getVersion());
            zooKeeper.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
