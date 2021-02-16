package lesson7;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*public class ThreadDemo2 {
    static class Worker extends Thread{
        private BlockingQueue<Runnable> queue=null;
        public Worker(BlockingQueue<Runnable> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable command = queue.take();
                    command.run();
                }
            } catch (InterruptedException e) {
                System.out.println("线程被终止")  ;
            }
        }
    }
    static class MyThreadPool{
        private BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        private List<Worker> workers=new ArrayList<>();
        private static final int maxWorkCount=10;


        public void execute(Runnable command) throws InterruptedException {
            if(workers.size()<maxWorkCount){
                Worker worker=new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            queue.put(command);
        }
        public void shutDown() throws InterruptedException {
            for (Worker worker:workers) {
                worker.interrupt();
            }
            for (Worker worker:workers) {
                worker.join();
            }
        }
    }
    static class Task implements Runnable{
        int num;
        public Task(int num){
            this.num=num;
        }

        @Override
        public void run() {
            System.out.println("正在执行任务"+num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool=new MyThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Task(i));
        }
        Thread.sleep(2000);
        pool.shutDown();
        System.out.println("线程池已经被销毁");
    }
}*/
/*public class ThreadDemo2{
    static class Worker extends Thread{
        private BlockingQueue<Runnable> queue=null;
        public Worker(BlockingQueue<Runnable> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable command = queue.take();
                    command.run();
                }
            } catch (InterruptedException e) {
                System.out.println("线程被终止了");
            }
        }
    }
    static class MyThreadPool{
        private BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        private List<Worker> workers=new ArrayList<>();
        private static final int maxWorkCount=10;

        public void execute(Runnable command) throws InterruptedException {
            if(workers.size()<maxWorkCount){
                Worker worker=new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            queue.put(command);
        }
        public void shutDown() throws InterruptedException {
            for (Worker worker:workers) {
                worker.interrupt();
            }
            for (Worker worker:workers) {
                worker.join();
            }
        }
    }
    static class Task implements Runnable{
        int num;
        public Task(int num){
            this.num=num;
        }

        @Override
        public void run() {
            System.out.println("正在执行任务"+num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool=new MyThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Task(i));
        }
        Thread.sleep(2000);
        pool.shutDown();
        System.out.println("线程池已经被销毁");
    }
}*/
/*
public class ThreadDemo2{

    static class Worker extends Thread{
        private BlockingQueue<Runnable> queue=null;
        public Worker(BlockingQueue<Runnable> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable command = queue.take();
                    command.run();
                }
            } catch (InterruptedException e) {
                System.out.println("线程被终止")  ;
            }
        }
    }
    static class Task implements Runnable{
        int num;
        public Task(int num){
            this.num=num;
        }
        @Override
        public void run() {
            System.out.println("正在执行任务"+num);
        }
    }
    static class MyThreadPool{
        private BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        private List<Worker> workers=new ArrayList<>();
        private static final int maxWorkCount=10;

        public void execute(Runnable command) throws InterruptedException {
            if(workers.size()<maxWorkCount){
                Worker worker=new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            queue.put(command);
        }
        public void shutDown() throws InterruptedException {
            for (Worker worker:workers) {
                worker.interrupt();
            }
            for (Worker worker:workers) {
                worker.join();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool=new MyThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Task(i));
        }
        Thread.sleep(2000);
        pool.shutDown();
        System.out.println("线程池已经被销毁");
    }
}
*/
public class ThreadDemo2{
    static class Worker extends Thread{
        private BlockingQueue<Runnable> queue=null;
        public Worker(BlockingQueue<Runnable> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()){
                    Runnable command = queue.take();
                    command.run();
                }
            } catch (InterruptedException e) {
                System.out.println("线程已经被中断");
            }
        }
    }
    static class MyThreadPool{
        private BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        private List<Worker> workers=new ArrayList<>();
        private static final int maxWorkCount=10;

        public void execute(Runnable command) throws InterruptedException {
            if(workers.size()<maxWorkCount){
                Worker worker=new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            queue.put(command);
        }
        public void shutDown() throws InterruptedException {
            for (Worker worker:workers) {
                worker.interrupt();
            }
            for (Worker worker:workers) {
                worker.join();
            }
        }
    }
    static class Task implements Runnable{
        int num;
        public Task(int num){
            this.num=num;
        }

        @Override
        public void run() {
            System.out.println("正在执行任务"+num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool =new MyThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Task(i));
        }
        Thread.sleep(2000);
        pool.shutDown();
        System.out.println("线程池被销毁");
    }
}
