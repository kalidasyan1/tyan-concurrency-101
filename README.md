# Java Concurrency Guide
* ChatGPT: https://chatgpt.com/g/g-p-684257b10984819185d9933cf84944df-system-design/c/6859e620-81d4-800b-a0fb-d58820a85f85
* [Java Concurrency Diagrams](src/main/resources/diagrams)

## Java Concurrency Best Practices and Performance Tuning
* Minimize lock scope and hold time.
* Prefer concurrent collections (ConcurrentHashMap, etc.) over manual synchronization.
* Use thread pools, avoid manual thread creation for short-lived tasks.
* Avoid deadlocks by consistent lock ordering.
* Use volatile and atomic variables for simple state updates.

## References
* [Java Concurrency Tutorial (Jenkov)](https://jenkov.com/tutorials/java-concurrency/index.html)
* [Java Docs: java.util.concurrent](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/package-summary.html)
