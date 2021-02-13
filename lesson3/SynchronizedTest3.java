package lesson3;

public class SynchronizedTest3 {
    /**
     * 有一个教室，座位有50个，同时有三个老师安排同学的座位，
     * 每个老师安排100个同学到这个教室。模拟使用多线程实现：
     * 三个线程同时启动来安排同学，
     * 同学可以循环操作来安排，直到座位安排满
     *
     */
    private static int COUNT = 50;

    public static void main(String[] args) {

        new Thread(new Task(10)).start();
        new Thread(new Task(20)).start();
        new Thread(new Task(20)).start();

    }

    private static class Task implements Runnable{

        //设置每个老师可以安排学生的数量
        private int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            for(int i=0; i<100; i++){//并发并行执行
                synchronized (Task.class){
                    if(COUNT>0 && num>0){//一定要再次判断，
                        COUNT--;
                        num--;
                        System.out.printf("%s: count=%s, num=%s\n",
                                Thread.currentThread().getName(), COUNT, num);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
