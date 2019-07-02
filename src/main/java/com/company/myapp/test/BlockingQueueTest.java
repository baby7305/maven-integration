package com.company.myapp.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class BlockingQueueTest {
    /**
     定义装队列数据的队列
     */
    public static class Basket{
        // 队列，能够容纳3个队列数据
        BlockingQueue<String> basket = new ArrayBlockingQueue<String>(10);

        // 生产队列数据，放入队列
        public void produce() throws InterruptedException{
            // put方法放入一个队列数据，若basket满了，等到basket有位置
            basket.put("An apple");
        }
        // 消费队列数据，从队列中取走
        public String consume() throws InterruptedException{
            // get方法取出一个队列数据，若basket为空，等到basket有队列数据为止
            String apple = basket.take();
            return apple;
        }

        public int getAppleNumber(){
            return basket.size();
        }

    }
    //　测试方法
    public static void testBasket() {
        // 建立一个装队列数据的队列
        final Basket basket = new Basket();
        // 定义队列数据生产者
        class Producer implements Runnable {
            public void run() {
                try {
                    while (true) {
                        // 生产队列数据
                        System.out.println("生产者准备生产队列数据："
                            + System.currentTimeMillis());
                        basket.produce();
                        System.out.println("生产者生产队列数据完毕："
                            + System.currentTimeMillis());
                        System.out.println("生产完后有队列数据："+basket.getAppleNumber()+"个");
                        // 休眠300ms
                        Thread.sleep(300);
                    }
                } catch (InterruptedException ex) {
                }
            }
        }
        // 定义队列数据消费者
        class Consumer implements Runnable {
            public void run() {
                try {
                    while (true) {
                        // 消费队列数据
                        System.out.println("消费者准备消费队列数据："
                            + System.currentTimeMillis());
                        basket.consume();
                        System.out.println("消费者消费队列数据完毕："
                            + System.currentTimeMillis());
                        System.out.println("消费完后有队列数据："+basket.getAppleNumber()+"个");
                    }
                } catch (InterruptedException ex) {
                }
            }
        }

        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        service.submit(producer);
        service.submit(consumer);
        // 程序运行10s后，所有任务停止
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        service.shutdownNow();
    }
    public static void main(String[] args) {
        BlockingQueueTest.testBasket();
    }
}
