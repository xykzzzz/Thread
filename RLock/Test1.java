package RLock;

import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("尝试获取锁");
                boolean flag = lock.tryLock();
                if(!flag){
                    System.out.println("没有获取到锁");
                    return;
                }
                System.out.println("获取到锁");
                lock.unlock();
            }
        };
        System.out.println("main 获取锁");
        lock.lock();
        t.start();
    }
}
