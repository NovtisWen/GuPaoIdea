package vip.wen.distribute.rmi.client.balance;

import java.util.List;

public interface LoadBalance {

    String selectHost(List<String> repos);
}
