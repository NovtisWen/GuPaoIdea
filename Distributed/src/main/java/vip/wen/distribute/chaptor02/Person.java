package vip.wen.distribute.chaptor02;

import java.io.Serializable;

public class Person implements Cloneable,Serializable {

    private String name;

    private Email email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (Person)super.clone();
    }

    /**
     * 通过实现Serializable，可以通过IO流实现深度克隆
     * @return
     */
    public Person deepClone(){
        return null;
    }
}
