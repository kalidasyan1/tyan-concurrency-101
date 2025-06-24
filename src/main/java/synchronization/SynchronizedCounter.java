package synchronization;

/**
 * Synchronized Blocks and Methods
 * 	•	Only one thread can execute a synchronized block/method of an object at a time.
 * Intrinsic Locks (Monitors)
 * 	•	Every Java object has an intrinsic lock (monitor).
 * 	•	Used by synchronized.
 */
public class SynchronizedCounter {
  private int count = 0;
  public synchronized void increment() { count++; }
  public synchronized int get() { return count; }

  public static void main(String[] args) throws Exception {
    SynchronizedCounter c = new SynchronizedCounter();
    Thread t1 = new Thread(() -> { for (int i = 0; i < 10000; i++) c.increment(); });
    Thread t2 = new Thread(() -> { for (int i = 0; i < 10000; i++) c.increment(); });
    t1.start(); t2.start();
    t1.join(); t2.join();
    System.out.println("Final counter: " + c.get()); // Always 20000
  }
}
