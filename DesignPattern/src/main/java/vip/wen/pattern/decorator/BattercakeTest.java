package vip.wen.pattern.decorator;

public class BattercakeTest {

    public static void main(String[] args){
        EggDecorator eggDecorator = new EggDecorator(new BaseBattercake());

        System.out.println(eggDecorator.getMsg());
        System.out.println(eggDecorator.getPrice());
    }
}
