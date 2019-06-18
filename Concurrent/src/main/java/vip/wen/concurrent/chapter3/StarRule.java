package vip.wen.concurrent.chapter3;

import javafx.scene.media.VideoTrack;

public class StarRule {

    static int x = 0;

    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            //use x = 10
        });
        x=10;
        t1.start();
    }

}
