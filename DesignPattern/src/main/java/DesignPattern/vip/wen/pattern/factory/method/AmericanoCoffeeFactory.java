package DesignPattern.vip.wen.pattern.factory.method;

import DesignPattern.vip.wen.pattern.factory.simple.Americano;
import DesignPattern.vip.wen.pattern.factory.simple.Coffee;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class AmericanoCoffeeFactory implements IMakeCofee{

    @Override
    public Coffee makeCoffee() {
        return new Americano();
    }
}
