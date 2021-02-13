package lesson3;


/**
 * 有一个教室，座位50个，同时有三个老师安排学生座位
 * 每个老师安排100个同学到这个教室，模拟使用多线程
 * 座位编号1-50、0-49，三个线程同时启动来安排同学
 * 同学可以循环操作来安排，直到座位安排满了
 */

/**
 * io密集型任务 线程创建的个数 2*cpu的核数
 * 计算密集型任务 线程创建个数 cpu的核数+1 计算公式 线程创建的个数=cpu核数/(1-阻塞系数)
 * 阻塞系数：真正执行任务的时间/任务的总时间
 */
public class SynchronizedTest2 {
    private volatile static int classroom=50;
    private synchronized static void robSeat(){
        classroom--;
    }

    public static void main(String[] args) {
        Thread []threads=new Thread[3];//代表三个老师
        for (int i = 0; i < 3; i++) {
            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        if(classroom>0){
                            robSeat();
                            System.out.println(Thread.currentThread().getName()+":"+classroom);
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }
        for (Thread t:threads){
            t.start();
        }
    }
}
