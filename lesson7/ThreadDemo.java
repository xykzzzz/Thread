package lesson7;

import java.util.concurrent.PriorityBlockingQueue;

public class ThreadDemo {
    static class Task implements Comparable<Task>{
        //借助run方法来描述具体执行的任务是啥
        private Runnable command;
        //表示啥时来执行这个任务
        private long time;
        //构造方法的参数表示，多少毫秒之后执行
        public Task(Runnable command, long after) {
            this.command = command;
            this.time = System.currentTimeMillis()+after;
        }
        //执行任务具体逻辑
        public void run(){
            command.run();
        }

        @Override
        public int compareTo(Task o) {
            return (int)(this.time-o.time);
        }
    }
    static class Worker extends Thread{
        private PriorityBlockingQueue<Task> queue=null;
        private Object mailBox=null;
        public Worker(PriorityBlockingQueue<Task> queue,Object mailBox){
            this.queue=queue;
           this.mailBox=mailBox;
        }
        @Override
        public void run(){
            while (true){
                try {
                    Task task=queue.take();
                    long curTime=System.currentTimeMillis();
                    if(task.time>curTime){
                        queue.put(task);
                        synchronized (mailBox){
                            mailBox.wait(task.time-curTime);
                        }
                    }else {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
    static class Timer{
        //为了避免忙等，需要使用wait方法
        private Object mailBox=new Object();
        //定时器三个构成
        //1.用一个类来描述任务
        //2.用一个阻塞优先队列来组织若干个任务，让队首的元素就是时间最早的任务，
        // 只需要检测队首元素就知道，如果队首元素时间未到，那麽其他元素肯定也不能执行
        //3.用一个线程来循环扫描当前的组赛队列的队首元素，如果时间到，就执行指定的任务
        private PriorityBlockingQueue<Task> queue =new PriorityBlockingQueue<>();
        public Timer(){
            Worker worker=new Worker(queue,mailBox);
            worker.start();
        }
        //还需要提供一个方法，让调用者能把任务安排进来
        public void schedule(Runnable command,long after){
            Task task=new Task(command,after);
            queue.put(task);
            synchronized (mailBox){
                mailBox.notify();
            }
        }
    }

    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hehe");
                timer.schedule(this,2000);
            }
        },2000);
    }
}
