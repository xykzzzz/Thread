package lesson4;



public class Singleton {
    //懒汉模式 单线程版
    private static volatile Singleton INSTANCE;
    private Singleton(){}
/*    public synchronized static Singleton getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE=new Singleton();
        }
        return INSTANCE;
    }*/
    //改正以上代码
    //懒汉模式 多线程版 二次判断 性能高
/*    public  static Singleton getINSTANCE(){
        if(INSTANCE==null){
            synchronized (Singleton.class){
                if(INSTANCE==null){
                    INSTANCE=new Singleton();
                }
            }
        }
        return INSTANCE;
    }*/
    public static Singleton getINSTANCE(){
        if(INSTANCE==null){
            synchronized (Singleton.class){
                if(INSTANCE==null){
                    INSTANCE=new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
