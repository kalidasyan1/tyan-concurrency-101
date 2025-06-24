package synchronization;

import java.util.concurrent.Semaphore;

/**
 * Semaphores
 * 	•	Used to control access to a resource.
 */
public class SemaphoreExample {
  private static final Semaphore sem = new Semaphore(2);

  public static void main(String[] args) {
    Runnable task = () -> {
      try {
        sem.acquire();
        System.out.println(Thread.currentThread().getName() + " acquired a permit.");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      } finally {
        System.out.println(Thread.currentThread().getName() + " releasing a permit.");
        sem.release();
      }
    };

    for (int i = 0; i < 5; i++) new Thread(task).start();
  }
}
