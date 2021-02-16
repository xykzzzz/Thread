package lesson6;

import java.security.Policy;
import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5, //核心线程数 正式员工
                10,//最大线程数 正式员工+临时员工
                60, //
                TimeUnit.SECONDS,//idle线程空闲时间 ：临时工最大存活时间，超过时间解雇
                new LinkedBlockingQueue<>(),//阻塞队列，快递公司任务存放的地方
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName()+"开始执行");
                            }
                        });
                    }
                },//创建线程的工厂类：线程池创建线程时，调用该 工厂类的方法来创建线程 对应招聘员工标准
                new ThreadPoolExecutor.AbortPolicy()
                /**
                 * 拒绝策略：最大线程数和阻塞队列已满，采取的拒绝策略
                 * AbortPolicy:直接抛RejectedException异常
                 * callerRunsPolicy:谁（某个线程）交给我（线程池）任务，我拒绝执行，由谁自己执行
                 * DisCardPolicy:交给我的任务直接丢弃
                 * DiscardOldestPolicy：丢弃阻塞队列最旧的任务
                 */
        ); //线程池创建后只要有任务就自动执行
        for (int i = 0; i < 20; i++) {
            final int j=i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        ExecutorService pool2=Executors.newSingleThreadExecutor();//单线程池 没有临时线程 只有正式线程
        ExecutorService pool3=Executors.newCachedThreadPool(); //缓存的线程池  只有临时线程 并且存活时间为60s
        ExecutorService pool4=Executors.newFixedThreadPool(4);//固定大小线程池  在第一个方法基础上自己决定有几个正式线程
        ScheduledExecutorService pool5=Executors.newScheduledThreadPool(4);//计划任务线程池

        pool5.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        },2,1,TimeUnit.SECONDS);

    }
}
