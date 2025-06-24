package synchronization;

/**
 * 8. Volatile Keyword
 * 	•	Ensures visibility (not atomicity).
 * 	•	Use for flags, state, not counters.
 * scenario:
 * 	•	A Worker thread that loops while a volatile boolean flag is true.
 * 	•	The main thread sleeps for a short time, then sets the flag to false.
 * 	•	The worker stops as soon as it sees the updated flag (thanks to volatile).
 */
public class VolatileFlagDemo {
  private volatile boolean running = true;

  public void run() {
    System.out.println("Worker started.");
    while (running) {
      // Simulate some work
      try { Thread.sleep(100); } catch (InterruptedException ignored) {}
    }
    System.out.println("Worker stopped.");
  }

  public void stop() {
    running = false;
    System.out.println("Worker stop requested.");
  }

  public static void main(String[] args) throws InterruptedException {
    VolatileFlagDemo demo = new VolatileFlagDemo();

    Thread workerThread = new Thread(demo::run);
    workerThread.start();

    // Main thread sleeps for a bit, then signals the worker to stop
    Thread.sleep(500);
    demo.stop();

    workerThread.join();
    System.out.println("Main thread exiting.");
  }
}
