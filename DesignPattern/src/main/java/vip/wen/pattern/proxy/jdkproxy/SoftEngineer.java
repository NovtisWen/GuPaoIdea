package vip.wen.pattern.proxy.jdkproxy;

import vip.wen.pattern.proxy.Person;

public class SoftEngineer implements Person {

    @Override
    public void findJob() {
        System.out.println("我要找的是软件工程师的工作");
    }

    @Override
    public void study() {
        System.out.println("我要学习软件开发");
    }
}
