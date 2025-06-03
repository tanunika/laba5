package shared.Structures;

import java.io.Serializable;

public class Location implements Serializable {
    private final double x;
    private final double y; //Поле не может быть null
    private final double z;

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX(){return x;}
    public double getY(){return y;}
    public double getZ(){return z;}

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + ", z=" + z + "}";
    }
}
