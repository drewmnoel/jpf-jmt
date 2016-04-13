import java.util.LinkedList;
import java.util.Random;

public class RandomList {
  public static void main(String args[]) {
    Random r = new Random();
    LinkedList<Integer> l = new LinkedList<>();

    // Create lots of list data at random
    while (r.nextBoolean()) {
      l.add(0);
    }
  }
}
