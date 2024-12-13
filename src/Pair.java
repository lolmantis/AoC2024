import java.util.Objects;

public class Pair {
  private final Integer X;
  private final Integer Y;

  Pair(Integer X, Integer Y) {
    this.X = X;
    this.Y = Y;
  }
  public boolean ComparePair(Pair p) {
    return X.equals(p.getX()) && Y.equals(p.getY());
  }
  public Integer getX() {
    return X;
  }
  public Integer getY() {
    return Y;
  }
  @Override
  public String toString() {
    return "{%d, %d}".formatted(X, Y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {return true;}
    if (obj == null) {return false;}

    if (obj instanceof Pair) {
      return this.ComparePair((Pair) obj);
    }
    return false;
  }

  @Override
  public int hashCode() {return Objects.hash(X, Y);}
}
