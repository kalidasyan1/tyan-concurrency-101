package thread.lifecycle;

/**
 * Different Usage Scenarios
 * 	•	Short-lived tasks: Use Thread directly.
 * 	•	Repeated/pooled tasks: Use ExecutorService
 */
public class SimpleThreadDemo {
  public static void main(String[] args) {
    Thread t = new Thread(() -> System.out.println("Hello from thread!"));
    t.start();
    System.out.println("Hello from main!");
  }
}
