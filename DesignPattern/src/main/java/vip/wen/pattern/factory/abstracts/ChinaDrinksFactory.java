package vip.wen.pattern.factory.abstracts;

import vip.wen.pattern.factory.simple.Coffee;
import vip.wen.pattern.factory.simple.Latte;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class ChinaDrinksFactory implements AbstractDrinksFactory{

    @Override
    public Coffee makeCoffee() {
        return new Latte();
    }

    @Override
    public Tea makeTea() {
        return new MiyeTea();
    }
}
