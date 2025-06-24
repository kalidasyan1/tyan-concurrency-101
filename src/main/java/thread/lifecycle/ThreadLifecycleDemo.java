package thread.lifecycle;

/**
 * Thread Lifecycle States
 * 	•	NEW
 * 	•	RUNNABLE
 * 	•	BLOCKED
 * 	•	WAITING
 * 	•	TIMED_WAITING
 * 	•	TERMINATED
 */
public class ThreadLifecycleDemo {
  public static void main(String[] args) throws InterruptedException {
    Thread t = new Thread(() -> {
      System.out.println("Thread running...");
    });

    System.out.println("State after creation: " + t.getState()); // NEW
    t.start();
    System.out.println("State after start: " + t.getState());    // RUNNABLE

    t.join();
    System.out.println("State after termination: " + t.getState()); // TERMINATED
  }
}
