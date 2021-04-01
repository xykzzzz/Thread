package RLock;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    static ReentrantLock lock=new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("尝试获取锁");
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("没有获取到锁");
                    e.printStackTrace();
                    return;
                }
                System.out.println("释放锁");
                lock.unlock();
            }
        };
        lock.lock();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
    }
}
