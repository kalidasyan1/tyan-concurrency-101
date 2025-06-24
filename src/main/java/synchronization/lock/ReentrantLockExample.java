package synchronization.lock;

import java.util.concurrent.locks.ReentrantLock;


/**
 * 	Explicit Locks: ReentrantLock
 * 	•	More flexibility than synchronized.
 * 	•	TryLock, timeout, interruptible locking, fairness policies.
 */
public class ReentrantLockExample {
  private final ReentrantLock lock = new ReentrantLock();
  private int count = 0;

  public void increment() {
    lock.lock();
    try { count++; } finally { lock.unlock(); }
  }

  public int get() {
    lock.lock();
    try { return count; } finally { lock.unlock(); }
  }

  public static void main(String[] args) throws Exception {
    ReentrantLockExample c = new ReentrantLockExample();
    Thread t1 = new Thread(() -> { for (int i = 0; i < 10000; i++) c.increment(); });
    Thread t2 = new Thread(() -> { for (int i = 0; i < 10000; i++) c.increment(); });
    t1.start(); t2.start();
    t1.join(); t2.join();
    System.out.println("Final counter: " + c.get());
  }
}
