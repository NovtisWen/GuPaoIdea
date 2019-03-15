package vip.wen.pattern.prototype.demo;

import java.util.List;

public class ConcreteProtypeA implements Prototype{

    private int age;

    private String name;

    private List hobbies;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getHobbies() {
        return hobbies;
    }

    public void setHobbies(List hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public Prototype clone() {
        ConcreteProtypeA concreteProtype = new ConcreteProtypeA();
        concreteProtype.setAge(this.age);
        concreteProtype.setName(this.name);
        concreteProtype.setHobbies(this.hobbies);
        return concreteProtype;
    }
}
