package vip.wen.pattern.factory.method;

import vip.wen.pattern.factory.simple.Cappuccino;
import vip.wen.pattern.factory.simple.Coffee;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class CappuccinoCoffeeFactory implements IMakeCofee{

    @Override
    public Coffee makeCoffee() {
        return new Cappuccino();
    }
}
