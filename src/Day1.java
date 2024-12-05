import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Hashtable;

public class Day1 {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(new File("./src/Day1.txt"));
    int TotalDistance = 0;
    int TotalSimilarity = 0;
    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();
    Hashtable<Integer, Integer> similarity = new Hashtable<>();
    while (in.hasNextInt()) {
      left.add(in.nextInt());
      right.add(in.nextInt());
      similarity.putIfAbsent(left.getLast(), 0);
    }
    in.close();
    left.sort(Collections.reverseOrder());
    right.sort(Collections.reverseOrder());
    for (int i = 0; i < left.size(); i++) {
      TotalDistance += Math.abs(left.get(i) - right.get(i));
    }
    System.out.println(TotalDistance);
    similarity.replaceAll(
      (k, v) -> Collections.frequency(right, k)
    );
    for (int key : similarity.keySet()) {
      TotalSimilarity += key*similarity.get(key);
    }
    System.out.println(TotalSimilarity);
  }
}
