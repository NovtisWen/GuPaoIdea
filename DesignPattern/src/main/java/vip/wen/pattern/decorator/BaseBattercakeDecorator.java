package vip.wen.pattern.decorator;

public abstract class BaseBattercakeDecorator extends Battercake{

    private BaseBattercake battercake;

    public BaseBattercakeDecorator(BaseBattercake battercake){
        this.battercake = battercake;
    }

    public abstract void doSomething();

    @Override
    public String getMsg() {
        return battercake.getMsg();
    }

    @Override
    public int getPrice() {
        return battercake.getPrice();
    }
}
