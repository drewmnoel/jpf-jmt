import java.util.LinkedList;
import java.util.Random;

/**
 * A very bad random list generator. Could run forever and eat up
 * all the memory. JPF will be used to catch it when it gets too big
 */
public class RandomList {
  /**
   * Entry point, entire program is here
   *
   * @param args[] Ignored
   */
  public static void main(String args[]) {
    Random r = new Random();
    LinkedList<Integer> l = new LinkedList<>();

    // Create lots of list data at random
    while (r.nextBoolean()) {
      l.add(0);
    }
  }
}
