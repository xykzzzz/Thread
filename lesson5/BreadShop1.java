package lesson5;

public class BreadShop1 {
    private static int COUNT;
    private static int produceMax;//面包生产的总数不会消费
    private static int consumer_NUM=10;//消费者数量
    private static int consumer_COUNT=5;//消费者每次消费面包数
    private static int produce_NUM=5;//生产者数量
    private static int produce_TIMES=10;//生产者生产次数
    private static int produces_COUNT=3;//每次生产面包的次数
    private static int max_COUNT=100;//最大库存

//消费者
    public static class  Consumer implements Runnable{
    private String name;

    public Consumer(String name) {
        this.name = name;
    }
        @Override
        public void run() {
            //一直消费
            try {
                while (true){
                    synchronized (BreadShop1.class){
                        if(produceMax==produce_NUM*produces_COUNT*produce_TIMES){
                            break;
                        }
                        //库存达到下线,不能继续消费，需要阻塞等待
                        if(COUNT-consumer_COUNT<0){
                            BreadShop1.class.wait();//让当前线程阻塞，释放对象锁,并唤醒等待队列竞争锁
                        }else{
                            System.out.printf("消费者 %s 消费了%s个面包, 库存%s\n", name, consumer_COUNT,COUNT);
                            COUNT-=consumer_COUNT;
                            //通知由BreadShop.class.wait()这个代码进入阻塞的线程
                            BreadShop1.class.notifyAll();
                            //模拟消费的耗时
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //生产者
    public static class  Producer implements Runnable{
        private String name;

        public Producer(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                for (int i = 0; i <produces_COUNT; i++) {
                    synchronized (BreadShop1.class){
                        if(COUNT+produces_COUNT>max_COUNT){
                            BreadShop1.class.wait();
                        }else {
                            //库存满足生产条件
                            System.out.printf("生产者 %s 生产了%s个面包, 库存%s,生产的数量%s\n", name, produces_COUNT,COUNT,max_COUNT);
                            COUNT+=produces_COUNT;
                            produceMax+=produces_COUNT;
                            //通知BreadShop.class.wait();代码进入阻塞的线程
                            BreadShop1.class.notifyAll();
                            //模拟消费的耗时
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
     //20个消费者
        Consumer[]consumers=new Consumer[consumer_NUM];
        //10个生产者
        Producer []producers=new Producer[produce_TIMES];
        for (int i = 0; i < consumer_NUM; i++) {
            consumers[i]=new Consumer(String.valueOf(i));
        }
        for (int i = 0; i < consumer_NUM; i++) {
            producers[i]=new Producer(String.valueOf(i));
        }
        for (Consumer consumer:consumers) {
            new Thread(consumer).start();
        }
        for (Producer producer:producers) {
            new Thread(producer).start();
        }

    }
}

/**
 * 面包店
 * 10个生产者，每个每次生产3个
 * 20个消费者，每个每次消费1个
 */
/*public class BreadShop {

    *//**
     * 面包店库存
     *//*
    private static int COUNT;

    *//**
     * 消费者
     *//*
    public static class Consumer implements Runnable{

        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            //一直消费
            try {
                while (true){
                    synchronized (BreadShop.class){
                        //库存到达下限，不能继续消费，需要阻塞等待
                        if(COUNT == 0){
                            BreadShop.class.wait();//释放对象锁
                        }else{
                            //库存满足消费条件，允许消费
                            COUNT--;
                            System.out.printf("消费者 %s 消费了1个面包, 库存%s\n", name, COUNT);
                            //通知BreadShop.class.wait();代码进入阻塞的线程
                            BreadShop.class.notifyAll();
                            //模拟消费的耗时
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //生产者
    public static class Producer implements Runnable{

        private String name;

        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                //达到生产次数
                while (true){
                    synchronized (BreadShop.class){
                        //库存到达上限，不能继续生产，需要阻塞等待
                        if(COUNT + 3 > 100){
                            BreadShop.class.wait();//释放对象锁
                        }else{
                            COUNT+=3;
                            //库存满足生产条件
                            System.out.printf("生产者 %s 生产了3个面包, 库存%s\n", name, COUNT);
                            //通知BreadShop.class.wait();代码进入阻塞的线程
                            BreadShop.class.notifyAll();
                            //模拟消费的耗时
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //同时启动消费者线程
        Thread[] consumers = new Thread[20];
        for(int i=0; i<20; i++){
            consumers[i] = new Thread(new Consumer(String.valueOf(i)));
        }
        //同时启动10个生产者线程
        Thread[] producers = new Thread[10];
        for(int i=0; i<10; i++){
            producers[i] = new Thread(new Producer(String.valueOf(i)));
        }

        for(Thread t : consumers){
            t.start();
        }
        for(Thread t : producers){
            t.start();
        }
    }
}*/
