package vip.wen.concurrent.chapter3;

public class Demo {

    int a =0;
    volatile boolean flag=false;

    public void writer(){//线程A
        a=1;        //1
        flag=true;  //2
        // 1 happens-before 2
    }

    public void reader(){
        //3 happens-before 4;
        if (flag){ //3
            //**
            int x = a; //4
        }
    }


}
