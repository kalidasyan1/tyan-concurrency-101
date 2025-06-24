package thread.lifecycle;

public class RunnableExample {
  public static void main(String[] args) {
    Runnable task = () -> {
      System.out.println("Running in: " + Thread.currentThread().getName());
    };
    new Thread(task).start();
  }
}
