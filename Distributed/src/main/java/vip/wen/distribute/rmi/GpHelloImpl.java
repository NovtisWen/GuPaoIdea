package vip.wen.distribute.rmi;

import vip.wen.distribute.rmi.anno.RpcAnnotation;

@RpcAnnotation(value = IGpHello.class)
public class GpHelloImpl implements IGpHello {

    @Override
    public String sayHello(String msg) {
        return "hello "+msg;
    }
}
