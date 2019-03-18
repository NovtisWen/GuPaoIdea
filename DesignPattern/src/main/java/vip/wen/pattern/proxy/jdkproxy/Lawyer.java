package vip.wen.pattern.proxy.jdkproxy;

import vip.wen.pattern.proxy.Person;

public class Lawyer implements Person {

    @Override
    public void findJob() {
        System.out.println("我要找的是律师相关工作");
    }

    @Override
    public void study() {
        System.out.println("我学习法律文献");
    }
}
