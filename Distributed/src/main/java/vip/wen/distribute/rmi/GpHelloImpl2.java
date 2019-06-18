package vip.wen.distribute.rmi;

import vip.wen.distribute.rmi.anno.RpcAnnotation;

@RpcAnnotation(value = IGpHello.class,version = "2.0")
public class GpHelloImpl2 implements IGpHello {

    @Override
    public String sayHello(String msg) {
        return "[I'm Version 2.0]hello "+msg;
    }
}
