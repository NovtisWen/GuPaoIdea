package vip.wen.pattern.factory.abstracts;

import vip.wen.pattern.factory.simple.Americano;
import vip.wen.pattern.factory.simple.Coffee;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class AmericaDrinksFactory implements AbstractDrinksFactory{

    @Override
    public Coffee makeCoffee() {
        return new Americano();
    }

    @Override
    public Tea makeTea() {
        return new GreenTea();
    }
}
