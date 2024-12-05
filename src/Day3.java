import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(new File("./src/Day3.txt"));
    Pattern pattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)");
    Integer tally = 0;
    boolean enabled = true;
    while (in.hasNextLine()) {
      Matcher matcher = pattern.matcher(in.nextLine());
      while (matcher.find()) {
        if (matcher.group().equals("don't()")) {
          enabled = false;
          continue;
        }
        else if (matcher.group().equals("do()")) {
          enabled = true;
          continue;
        }
        if (enabled) {
        Integer[] cut = Arrays.stream(
          matcher.group().substring(4, matcher.group().length() - 1).split(",")
          ).map(Integer::valueOf).toArray(Integer[]::new);
        tally += cut[0]*cut[1];
        }
      }
    }
    in.close();
    System.out.println(tally);
  }
}
