package StudyGroup;

public class Location {
    private final float x;
    private final float y; //Поле не может быть null
    private final float z;

    public Location(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX(){return x;}
    public float getY(){return y;}
    public float getZ(){return z;}

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + ", z=" + z + "}";
    }
}
