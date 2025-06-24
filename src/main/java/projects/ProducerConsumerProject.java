package projects;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Implement a classic Producer-Consumer using ExecutorService and BlockingQueue.
 * 	1.	Create a BlockingQueue for shared data.
 * 	2.	Use ExecutorService for producer and consumer pools.
 * 	3.	Producers put items; consumers take and process items.
 */
public class ProducerConsumerProject {
  BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

  ExecutorService producers = Executors.newFixedThreadPool(2);
  ExecutorService consumers = Executors.newFixedThreadPool(2);

  Runnable producer = () -> {
    for (int i = 0; i < 10; i++) {
      String threadName = Thread.currentThread().getName();
      try {
        String task = threadName + "_" + i;
        queue.put(task);
        System.out.printf("Produced: %s%n", task);
      } catch (InterruptedException e) {
      }
    }
  };

  Runnable consumer = () -> {
    for (int i = 0; i < 10; i++) {
      try {
        String task = queue.take();
        String threadName = Thread.currentThread().getName();
        System.out.printf("Consumer %s consumed: %s%n", threadName, task);
      } catch (InterruptedException e) {
      }
    }
  };

  public void run() throws InterruptedException {
    for (int i = 0; i < 2; i++) {
      producers.submit(producer);
    }

    for (int i = 0; i < 2; i++) {
      consumers.submit(consumer);
    }

    producers.shutdown();
    consumers.shutdown();
    producers.awaitTermination(1, TimeUnit.MINUTES);
    consumers.awaitTermination(1, TimeUnit.MINUTES);
    System.out.println("Producer-Consumer simulation complete.");
  }

  public static void main(String[] args) throws InterruptedException {
    new ProducerConsumerProject().run();
  }
}
