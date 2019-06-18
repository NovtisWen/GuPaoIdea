package vip.wen.concurrent.chapter6;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class App {

    static ConcurrentHashMap chm = new ConcurrentHashMap();

    public static void main(String[] args){
        //Collections.synchronizedMap();
        chm.put("1","1");
        chm.size();
    }
}
