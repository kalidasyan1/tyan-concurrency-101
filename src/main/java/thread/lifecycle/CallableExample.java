package thread.lifecycle;

import java.util.concurrent.*;

public class CallableExample {
  public static void main(String[] args) throws Exception {
    Callable<String> task = () -> "Callable Result";
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<String> future = executor.submit(task);
    System.out.println(future.get()); // Blocks until result available
    executor.shutdown();
  }
}
