package vip.wen.pattern.prototype.demo;

public class Client {
    private Prototype prototype;

    public Client(Prototype prototype){
        this.prototype = prototype;
    }

    public Prototype startClone(Prototype concreteProtype){

        return (ConcreteProtypeA)concreteProtype.clone();
    }


}
