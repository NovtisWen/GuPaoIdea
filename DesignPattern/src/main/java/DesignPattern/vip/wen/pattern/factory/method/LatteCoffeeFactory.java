package DesignPattern.vip.wen.pattern.factory.method;

import DesignPattern.vip.wen.pattern.factory.simple.Coffee;
import DesignPattern.vip.wen.pattern.factory.simple.Latte;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class LatteCoffeeFactory implements IMakeCofee{

    @Override
    public Coffee makeCoffee() {
        return new Latte();
    }
}
