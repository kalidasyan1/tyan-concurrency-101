package thread.pool;

import java.util.concurrent.*;


/**
 * Java’s ExecutorService provides a flexible way to manage threads and resources.
 * Usage Scenarios
 * 	•	IO-bound tasks: Use cached thread pool.
 * 	•	CPU-bound tasks: Use fixed thread pool sized to #cores.
 */
public class ThreadPoolDemo {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(3);

    for (int i = 0; i < 5; i++) {
      int taskID = i;
      pool.submit(() -> {
        System.out.println("Task " + taskID + " run by " + Thread.currentThread().getName());
      });
    }
    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.MINUTES);
  }
}
