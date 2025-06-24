package synchronization;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Atomic Variables
 * 	•	Lock-free, faster for simple operations.
 * 	•	Examples: AtomicInteger, AtomicReference
 */
public class AtomicExample {
  public static void main(String[] args) throws Exception {
    AtomicInteger ai = new AtomicInteger(0);
    Thread t1 = new Thread(() -> { for (int i = 0; i < 10000; i++) ai.incrementAndGet(); });
    Thread t2 = new Thread(() -> { for (int i = 0; i < 10000; i++) ai.incrementAndGet(); });
    t1.start(); t2.start();
    t1.join(); t2.join();
    System.out.println("Final value: " + ai.get());
  }
}

