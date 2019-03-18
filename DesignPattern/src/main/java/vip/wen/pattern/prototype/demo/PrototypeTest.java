package vip.wen.pattern.prototype.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrototypeTest {

    /**
     * 浅克隆
     * @param args
     */
    public static void main(String[] args){
        //创建一个需要克隆的对象
        ConcreteProtypeA concreteProtypeA = new ConcreteProtypeA();
        concreteProtypeA.setAge(18);
        concreteProtypeA.setName("Noctis");
        List hobbies = new ArrayList();
        concreteProtypeA.setHobbies(hobbies);
        System.out.println(concreteProtypeA);

        Client client = new Client(concreteProtypeA);

        ConcreteProtypeA concreteProtypeClone = (ConcreteProtypeA)client.startClone(concreteProtypeA);

        System.out.println(concreteProtypeClone);
        System.out.println("克隆中的引用类型地址值："+concreteProtypeClone.getHobbies());
        System.out.println("克隆对象中的引用类型地址值"+concreteProtypeA.getHobbies());
        System.out.println("克隆对象地址比较："+(concreteProtypeClone.getHobbies()==concreteProtypeA.getHobbies()));

    }
}
