package vip.wen.pattern.prototype.demo;

import java.util.List;

public class ConcreteProtypeB implements Prototype{

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
        ConcreteProtypeB concreteProtype = new ConcreteProtypeB();
        concreteProtype.setAge(this.age);
        concreteProtype.setName(this.name);
        concreteProtype.setHobbies(this.hobbies);
        return concreteProtype;
    }
}
