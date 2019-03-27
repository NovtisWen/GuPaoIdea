package vip.wen.pattern.decorator;

public class EggDecorator extends BaseBattercakeDecorator{

    public EggDecorator(BaseBattercake battercake) {
        super(battercake);
    }

    @Override
    public void doSomething() {
        
    }

    @Override
    public String getMsg() {
        return super.getMsg()+"，加了一个鸡蛋";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+1;
    }
}
