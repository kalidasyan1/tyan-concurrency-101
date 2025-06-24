package synchronization;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 * 	•	Waits for a fixed number of threads to reach a common barrier.
 */
public class CyclicBarrierExample {
  public static void main(String[] args) {
    CyclicBarrier barrier = new CyclicBarrier(3, () -> {
      System.out.println("All threads reached the barrier. Proceeding...");
    });

    Runnable worker = () -> {
      System.out.println(Thread.currentThread().getName() + " waiting at barrier.");
      try {
        barrier.await();
        System.out.println(Thread.currentThread().getName() + " passed the barrier.");
      } catch (Exception e) {}
    };

    for (int i = 0; i < 3; i++) new Thread(worker).start();
  }
}
