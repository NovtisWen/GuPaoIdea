package vip.wen.pattern.decorator;

public class BattercakeTest {

    public static void main(String[] args){
        Battercake battercake ;
        battercake = new BaseBattercake();
        battercake = new EggDecorator(battercake);

        battercake = new EggDecorator(battercake);

        System.out.println(battercake.getMsg());
        System.out.println(battercake.getPrice());
    }
}
