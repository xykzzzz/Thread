package lesson4;



public class Singleton1 {
    //饿汉模式
    private static Singleton1 INSTANCE=new Singleton1();
    private Singleton1() {}
    public static Singleton1 getInstance(){
        return INSTANCE;
    }
}
