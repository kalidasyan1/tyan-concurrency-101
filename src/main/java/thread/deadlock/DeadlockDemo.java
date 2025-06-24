package thread.deadlock;

/**
 * Deadlock
 * 	•	When two or more threads are waiting forever for locks held by each other.
 * 	How to Avoid
 * 	•	Always acquire locks in the same order.
 * 	•	Use timeouts with lock attempts.
 */
public class DeadlockDemo {
  private static final Object LockA = new Object();
  private static final Object LockB = new Object();

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      synchronized (LockA) {
        System.out.println("Thread 1 acquired LockA");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (LockB) {
          System.out.println("Thread 1 acquired LockB");
        }
      }
    });

    Thread t2 = new Thread(() -> {
      synchronized (LockB) {
        System.out.println("Thread 2 acquired LockB");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (LockA) {
          System.out.println("Thread 2 acquired LockA");
        }
      }
    });

    t1.start();
    t2.start();
  }
}
