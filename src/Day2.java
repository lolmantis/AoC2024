import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day2 {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(new File("./src/Day2.txt"));
    int safe = 0;
    while (in.hasNextLine()) {
      Integer[] intValues = Arrays.stream(in.nextLine().split(" "))
        .map(Integer::valueOf).toArray(Integer[]::new);
      ArrayList<Integer> subArrays = new ArrayList<>(
        Arrays.asList(intValues)
      );
      for (int removalIndex = -1; removalIndex < subArrays.size(); removalIndex++) {
        boolean increasing = false, decreasing = false, safeDistance = true;
        Integer[] words;
        if (removalIndex != -1) {
          words = new Integer[subArrays.size()-1];
          int difference = 0;
          for (int i = 0; i < subArrays.size(); i++) {
            if (removalIndex == i) {
              difference = 1;
              continue;
            }
            words[i-difference] = subArrays.get(i);
          }
        }
        else {
          words = intValues;
        }
        for (int i = 0; i < words.length-1; i++) {
          int first = words[i], second = words[i+1];
          int diff = Math.abs(first - second);
          boolean unsafeRange = (diff < 1 || diff > 3);
          if (first < second) {
            increasing = true;
          }
          else if (first > second) {
            decreasing = true;
          }
          if (unsafeRange) {
            safeDistance = false;
          }
        }
        if (!(increasing && decreasing) && safeDistance) {
          safe++;
          break;
        }
      }
    }
    in.close();
    System.out.println(safe);
  }
}
