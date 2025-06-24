package projects.distributed;

import java.util.concurrent.*;
import java.util.*;


/**
 * Distributed task executor:
 * 	•	Each node takes tasks from a shared queue (could be implemented using a distributed queue, like Redis or Kafka).
 * 	•	Each task is executed only once (simulate with in-memory list for demonstration).
 * 	To Scale Up (Real Distributed System):
 * 	•	Use Redis/Kafka for the queue.
 * 	•	Use distributed locks (e.g., with Redisson or Zookeeper).
 * 	•	Ensure idempotency by tracking processed tasks in a distributed DB.
 *
 * 	Double-Checked Set (processed.add(task))
 * 	•	After dequeuing a task, each node tries to add it to the processed set.
 * 	•	processed.add(task) returns true only if the task was not already present in the set.
 * 	•	If it returns true, the thread has “won” the right to process the task and proceeds.
 * 	•	If it returns false, the thread skips processing (in case of a race).
 * 	But why do we need this extra set?
 * 	•	In practice, with ConcurrentLinkedQueue.poll(), this set isn’t strictly necessary for “exactly-once” in a single-process simulation.
 * 	•	However, if you extend to a real distributed system (with multiple processes/nodes), the queue and the “has this been processed?” check need to be coordinated atomically—which is what this extra set simulates.
 * 	•	Even locally, if two threads somehow manage to get the same task (e.g., if the task queue was not thread-safe or some code bug), the set prevents double-processing.
 */
public class DistributedTaskExecutor {
  private static final Queue<String> taskQueue = new ConcurrentLinkedQueue<>();
  private static final Set<String> processed = ConcurrentHashMap.newKeySet();

  public static void main(String[] args) {
    // Simulate tasks
    for (int i = 1; i <= 10; i++) taskQueue.add("Task-" + i);

    // Simulate 3 nodes (threads)
    Runnable node = () -> {
      String task;
      while ((task = taskQueue.poll()) != null) {
        if (processed.add(task)) { // Simulate distributed atomic check
          System.out.println(Thread.currentThread().getName() + " processed " + task);
          try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
      }
    };

    Thread t1 = new Thread(node, "Node-1");
    Thread t2 = new Thread(node, "Node-2");
    Thread t3 = new Thread(node, "Node-3");

    t1.start(); t2.start(); t3.start();
    try { t1.join(); t2.join(); t3.join(); } catch (InterruptedException e) {}
    System.out.println("All tasks processed in distributed fashion.");
  }
}
