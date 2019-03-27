package vip.wen.pattern.decorator;

public class BaseBattercake extends Battercake{
    @Override
    public String getMsg() {
        return "一个普通的煎饼";
    }

    @Override
    public int getPrice() {
        return 5;
    }
}
