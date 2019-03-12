package vip.wen.pattern.singleton.demo;

public class LazyDoubleSingleton {

    private static LazyDoubleSingleton singleton = null;

    private LazyDoubleSingleton(){}

    public static LazyDoubleSingleton getInstance(){
        if(singleton == null){
            synchronized (LazyDoubleSingleton.class){
                if(singleton == null){
                    singleton = new LazyDoubleSingleton();
                }
            }
        }
        return singleton;
    }


}
