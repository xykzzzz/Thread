package lesson4;



public class Singleton2 {
    //懒汉模式 单线程条件下的单列模式
    private   static Singleton2 INSTANCE1;
    private Singleton2() {}
    public static Singleton2 getInstance(){
        if(INSTANCE==null){
            INSTANCE=new Singleton2();
        }
        return INSTANCE;
    }
    //懒汉模式 多线程条件下单列模式
    //但是这种多线程下的单列模式，性能低，表现在一个时间段只能有一个线程通过引用操作对象
    //有一个这样的需求 既满足单列模式，还要并发并行的使用多个线程操作同一个对象
    public synchronized static Singleton2 getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE=new Singleton2();
        }
        return INSTANCE;
    }
    //改正上面代码满足以上需求 ,需要改变共享变量上加volatile
    //懒汉模式-多线程版-二次判断-性能高
    private  volatile static Singleton2 INSTANCE;

    /**
     * volatile关键字理解：
     * 保证可见性:意思是，每个线程都必须强制从主内存中读数据，而不是从自己工作内存中读数据
     * 禁止指令重排序,建立内存屏障:指的是对这个变量所在的指令禁止重排序
     * 在这个变量之前的是可以进行重排序的，这行指令建立了内存屏障
     *不保证原子性:count++不能达到预期的20000次，一个线程在进行时，可能会插入另外线程指令，
     * 保证不了原子性
     */
    public static Singleton2 getINSTANCE2(){
        //满足了性能 当同时有多个线程时，一个线程竞争到对象锁，new了对象，
        // 后续线程只需要if判断，不需要在获取锁，直接操作对象
        if(INSTANCE==null){
            //多个线程进来竞争锁，如果一个线程抢到了锁，其他线程需要进行休眠状态，
            //锁不是锁住了代码，而是对同一个共享的数据，在一个线程进行指令操作这个数据时，
            //其他线程不允许操作有关这个数据的指令，直到释放锁后，其他线程抢到锁，
            //才可以对这个数据的指令进行操作
            synchronized (Singleton2.class){
                //if时为了满足单列模式，假设没有if
                //多个线程同时执行这个方法时，第一个if，都会进来，进来之后，
                //没有if判断，每个线程都会new对象 不满足单列模式
                if(INSTANCE==null){
                    INSTANCE=new Singleton2();
                }
            }
        }
        return INSTANCE;
    }
}
