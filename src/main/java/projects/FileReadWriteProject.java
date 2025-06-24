package projects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Read and update a shared file using multiple threads safely.
 * 	1.	Use a ReentrantReadWriteLock to protect file access.
 * 	2.	Spawn readers and writers with threads.
 */
public class FileReadWriteProject {
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final String fileName = "shared.txt";

  public void write(String data) throws IOException {
    lock.writeLock().lock();
    try (FileWriter fileWriter = new FileWriter(fileName, true)) {
      String threadName = Thread.currentThread().getName();
      fileWriter.write(data + "\n");
      System.out.println(threadName + " wrote: " + data);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void read() throws IOException {
    lock.readLock().lock();
    String threadName = Thread.currentThread().getName();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      System.out.println(threadName + " reading file:");
      bufferedReader.lines().forEach(System.out::println);
    } catch (Exception e) {
      System.out.println(threadName + " failed to read file because of " + e);
    }finally {
      lock.readLock().unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    File file = new File("shared.txt");
    if (file.exists()) {
      file.delete();
      file.createNewFile();
    }

    FileReadWriteProject project = new FileReadWriteProject();

    Runnable writer = () -> {
      try {
        project.write("Data from " + Thread.currentThread().getName());
      } catch (IOException e) {
      }
    };

    Runnable reader = () -> {
      try {
        project.read();
      } catch (IOException e) {
      }
    };

    Thread t1 = new Thread(writer, "writer1");
    Thread t2 = new Thread(writer, "writer2");
    Thread t3 = new Thread(reader, "reader1");
    Thread t4 = new Thread(reader, "reader2");
    Thread t5 = new Thread(reader, "reader3");

    t1.start();
    // Sleep for 100 millis, otherwise, readers will all finished before the writer finished writing data.
    Thread.sleep(100);
    t3.start();
    t4.start();
    t2.start();
    t5.start();
    t1.join();
    t3.join();
    t4.join();
    t2.join();
    t5.join();
    System.out.println("File read/write simulation complete.");
  }
}
