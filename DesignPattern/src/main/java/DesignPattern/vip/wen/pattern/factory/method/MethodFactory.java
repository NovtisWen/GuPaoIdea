package DesignPattern.vip.wen.pattern.factory.method;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class MethodFactory {

    public static void main(String[] args){
        IMakeCofee americanoFactory = new AmericanoCoffeeFactory();
        americanoFactory.makeCoffee().getCoffe();

        IMakeCofee cappuccinoFactory = new CappuccinoCoffeeFactory();
        cappuccinoFactory.makeCoffee().getCoffe();

        IMakeCofee latteFactory = new LatteCoffeeFactory();
        latteFactory.makeCoffee().getCoffe();

    }
}
