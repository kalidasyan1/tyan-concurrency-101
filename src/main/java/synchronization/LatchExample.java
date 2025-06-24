package synchronization;

import java.util.concurrent.CountDownLatch;


/**
 * CountDownLatch
 * 	•	Waits for other threads to complete.
 */
public class LatchExample {
  public static void main(String[] args) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(3);

    Runnable worker = () -> {
      System.out.println(Thread.currentThread().getName() + " finished task.");
      latch.countDown();
    };

    for (int i = 0; i < 3; i++) new Thread(worker).start();

    latch.await();
    System.out.println("All tasks finished. Main thread proceeds.");
  }
}
