package vip.wen.pattern.factory.abstracts;

import vip.wen.pattern.factory.simple.Coffee;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public interface AbstractDrinksFactory {

    Coffee makeCoffee();

    Tea makeTea();
}
