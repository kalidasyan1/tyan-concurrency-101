package synchronization.lock;

import java.util.concurrent.locks.*;
import java.util.*;


/**
 * 	ReadWriteLock:
 * 	•	Allows multiple readers or one writer.
 * 	•	Good for caches or resources mostly read, rarely written.
 */
public class ReadWriteLockExample {
  private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private int data = 0;

  public void write(int val, String threadName) {
    rwLock.writeLock().lock();
    try {
      System.out.println(threadName + " acquiring write lock...");
      data = val;
      System.out.println(threadName + " wrote data: " + data);
      Thread.sleep(200); // Simulate some work
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      System.out.println(threadName + " releasing write lock.");
      rwLock.writeLock().unlock();
    }
  }

  public int read(String threadName) {
    rwLock.readLock().lock();
    try {
      System.out.println(threadName + " acquiring read lock...");
      Thread.sleep(100); // Simulate some work
      System.out.println(threadName + " read data: " + data);
      return data;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return -1;
    } finally {
      System.out.println(threadName + " releasing read lock.");
      rwLock.readLock().unlock();
    }
  }

  public static void main(String[] args) throws Exception {
    ReadWriteLockExample example = new ReadWriteLockExample();

    Runnable writer = () -> {
      String tName = Thread.currentThread().getName();
      for (int i = 1; i <= 3; i++) {
        example.write(i * 10, tName);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
      }
    };

    Runnable reader = () -> {
      String tName = Thread.currentThread().getName();
      for (int i = 0; i < 5; i++) {
        example.read(tName);
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}
      }
    };

    List<Thread> threads = new ArrayList<>();
    threads.add(new Thread(writer, "Writer-1"));
    threads.add(new Thread(writer, "Writer-2"));
    threads.add(new Thread(reader, "Reader-1"));
    threads.add(new Thread(reader, "Reader-2"));
    threads.add(new Thread(reader, "Reader-3"));

    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();

    System.out.println("All threads finished.");
  }
}
