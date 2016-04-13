import java.util.LinkedList;

public class ProducerConsumer {
  public void run() {
    LinkedList<Integer> l = new LinkedList<>();
    Producer p = new Producer(l);
    Consumer c = new Consumer(l);
    p.start();
    c.start();
  }

  public static void main(String args[]) {
    ProducerConsumer pc = new ProducerConsumer();
    pc.run();
  }

  private static class Producer extends Thread {
    LinkedList<Integer> l;
    public Producer(LinkedList<Integer> l) {
      this.l = l;
    }

    public void run() {
      for (int i = 0; i < 10; i++) {
        l.add(0);
        System.out.println("[+] Produced");
        try {
          this.sleep(125);
        } catch (InterruptedException iex) {}
      }
    }
  }

  private static class Consumer extends Thread {
    LinkedList<Integer> l;
    public Consumer(LinkedList<Integer> l) {
      this.l = l;
    }

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
