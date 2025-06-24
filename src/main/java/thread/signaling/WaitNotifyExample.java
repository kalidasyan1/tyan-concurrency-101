package thread.signaling;

/**
 * Inter-thread Communication
 * wait(), notify(), notifyAll()
 * 	•	Used for signaling between threads.
 * 	•	Must be called inside synchronized blocks.
 */
public class WaitNotifyExample {
  private static final Object lock = new Object();
  private static int shared = 0;

  public static void main(String[] args) {
    Thread producer = new Thread(() -> {
      synchronized (lock) {
        shared = 1;
        System.out.println("Producer produced.");
        lock.notify();
      }
    });

    Thread consumer = new Thread(() -> {
      synchronized (lock) {
        while (shared == 0) {
          try {
            lock.wait();
          } catch (InterruptedException e) {}
        }
        System.out.println("Consumer consumed: " + shared);
      }
    });

    consumer.start();
    producer.start();
  }
}
