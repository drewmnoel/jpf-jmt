import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomList {
  public static void main(String args[]) {
    Random r = new Random();
    List<Integer> l = new LinkedList<>();

    // Create some list data
    for (int i = 0; i < 4; i++) {
      l.add(i);
    }

    // Remove them in random order
    while(l.size() > 0) {
      l.remove(r.nextInt(l.size()));
    }
  }
}
