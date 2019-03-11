package vip.wen.pattern.factory.simple;

/**
 * @author wenbo
 * @date 2019-03-10
 */
public class CoffeeFactory {
    //可深入通过反射进行创建
    public Coffee makeCoffee(String name){
        if ("Latte".equals(name)){
            return new Latte();
        }else if("Americano".equals(name)){
            return new Americano();
        }else if("Cappuccino".equals(name)){
            return new Cappuccino();
        }
        return null;
    }

    public static void main(String[] args){
        CoffeeFactory factory = new CoffeeFactory();
        factory.makeCoffee("Latte").getCoffe();
        factory.makeCoffee("Americano").getCoffe();
        factory.makeCoffee("Cappuccino").getCoffe();
        
    }
}
