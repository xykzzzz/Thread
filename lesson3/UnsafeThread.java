package lesson3;
//有一个变量count=0，同时启动20个线程，每个线程循环1000次，
//每次循环count++，main线程等待20个线程执行完毕打印count（预期20000）
public class UnsafeThread {
    private static int count=0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        //尽量同时启动，不让new Thread耗时影响
        Thread[] threads=new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        count++;
                    }
                }
            });
        }
        for (Thread t:threads) {
            t.start();
        }
        //让main线程阻塞等待所有的20个子线程执行完毕
        for (Thread t:threads) {
            t.join();
        }
        System.out.println(count);
    }
}
