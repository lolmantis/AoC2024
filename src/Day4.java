import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4 {

  public static final int[][] DIRECTIONS = new int[][]{
    {-1, 0}, {-1, 1},
    {0, 1}, {1, 1},
    {1, 0}, {1, -1},
    {0, -1}, {-1, -1}
  };

  private static char[][] readFile() throws FileNotFoundException {
    char[][] letters = new char[140][140];
    Scanner in = new Scanner(new File("./testfiles/Day4.txt"));
    int currentLine = 0;
    while (in.hasNextLine()) {
      letters[currentLine++] = in.nextLine().toCharArray();
    }
    in.close();
    return letters;
  }

  private static void partOne() throws FileNotFoundException {
    char[][] letters = readFile();
    int tally = 0;
    char[] XMAS = new char[]{'X','M','A','S'};
    for (int row = 0; row < letters.length; row++) {
      for (int col = 0; col < letters[0].length; col++) {
        if (letters[row][col] != 'X') {continue;}
        for (int[] direction : DIRECTIONS) {
          int newRow = row + direction[0];
          int newCol = col + direction[1];
          int step = 1;
          while (
            newRow >= 0 && newRow < letters.length && newCol >= 0 && newCol < letters[row].length
            && step < XMAS.length && letters[newRow][newCol] == XMAS[step]
          ) {
            newRow += direction[0];
            newCol += direction[1];
            step++;
          }
          tally += step == 4 ? 1 : 0;
        }
      }
    }
    System.out.println(tally);
  }

  private static void partTwo() throws FileNotFoundException {
    char[][] letters = readFile();
    int tally = 0;
    for (int row = 0; row < letters.length; row++) {
      for (int col = 0; col < letters[0].length; col++) {
        if (letters[row][col] != 'A'
          || row == 0 || col == 0
          || row == letters.length - 1
          || col == letters[0].length - 1
        ) {continue;}
        tally +=
          (
          letters[row - 1][col - 1] == 'M' && letters[row + 1][col + 1] == 'S'
          ||
          letters[row - 1][col - 1] == 'S' && letters[row + 1][col + 1] == 'M'
          ) && (
          letters[row - 1][col + 1] == 'M' && letters[row + 1][col - 1] == 'S'
          ||
          letters[row - 1][col + 1] == 'S' && letters[row + 1][col - 1] == 'M'
        ) ? 1 : 0;
      }
    }
    System.out.println(tally);
  }

  public static void main(String[] args) throws FileNotFoundException {
    partOne();
    partTwo();
  }
}
