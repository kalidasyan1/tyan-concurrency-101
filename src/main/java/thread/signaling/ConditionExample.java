package thread.signaling;

import java.util.concurrent.locks.*;

/**
 * Condition Variables
 * 	•	Provided by Lock interface (newCondition()).
 * 	•	More flexible than wait/notify.
 */
public class ConditionExample {
  private static final Lock lock = new ReentrantLock();
  private static final Condition cond = lock.newCondition();
  private static int shared = 0;

  public static void main(String[] args) {
    Thread producer = new Thread(() -> {
      lock.lock();
      try {
        shared = 1;
        System.out.println("Producer produced.");
        cond.signal();
      } finally {
        lock.unlock();
      }
    });

    Thread consumer = new Thread(() -> {
      lock.lock();
      try {
        while (shared == 0) {
          cond.awaitUninterruptibly();
        }
        System.out.println("Consumer consumed: " + shared);
      } finally {
        lock.unlock();
      }
    });

    consumer.start();
    producer.start();
  }
}
