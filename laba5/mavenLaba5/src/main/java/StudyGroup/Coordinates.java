package StudyGroup;

public class Coordinates {
    private final double x;
    private final long y;

    public Coordinates(double x, Long y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public long getY() { return y; }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + "}";
    }
}