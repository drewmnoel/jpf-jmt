import java.util.LinkedList;

/**
 * A simple implementation of the Producer/Consumer problem
 * Intentionally implemented poorly. JPF will be used to determine
 * if there is ANY possible execution path that doesn't double the
 * starting memory usage
 */
public class ProducerConsumer {
  /**
   * Main method, starts up the threads and lets them run
   * @param args[] Ignored
   */
  public static void main(String args[]) {
    LinkedList<Integer> l = new LinkedList<>();
    Producer p = new Producer(l);
    Consumer c = new Consumer(l);
    p.start();
    c.start();
  }

  /**
   * Private class to represent the producer thread
   */
  private static class Producer extends Thread {
    /** A LinkedList shared with the Consumer thread */
    LinkedList<Integer> l;

    /**
     * Constructor, sets up the thread for production
     * @param  l A LinkedList to produce with
     */
    public Producer(LinkedList<Integer> l) {
      this.l = l;
    }

    /**
     * Thread entry point, produces 10 ints into the LinkedList
     * and waits in-between productions.
     */
    public void run() {
      for (int i = 0; i < 10; i++) {
        // Add an uninteresting zero to the list
        l.add(0);
        System.out.println("[+] Produced");

        // Attempt to sleep, but don't care if interrupted
        try {
          this.sleep(125);
        } catch (InterruptedException iex) {}
      }
    }
  }

  /**
   * Private class to represent the consumer thread
   */
  private static class Consumer extends Thread {
    /** A LinkedList shared with the Consumer thread */
    LinkedList<Integer> l;

    /**
     * Constructor, sets up the thread for consumption
     * @param  l A LinkedList to consume from
     */
    public Consumer(LinkedList<Integer> l) {
      this.l = l;
    }

    /**
     * Thread entry point, consumes 10 ints from the LinkedList
     * and waits a long time in-between consuming.
     */
    public void run() {
      for (int i = 0; i < 10; i++) {
        if (l.size() > 0) {
          System.out.println("[-] Consumed");
        }
        try {
          this.sleep(500);
        } catch (InterruptedException iex) {}
      }
    }
  }
}
