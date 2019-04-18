package vip.wen.distribute.rmi;

public class GpHelloImpl implements IGpHello {

    @Override
    public String sayHello(String msg) {
        return "hello "+msg;
    }
}
