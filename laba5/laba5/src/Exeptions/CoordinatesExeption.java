package Exeptions;

import StudyGroup.Coordinates;

import java.util.Objects;

public class CoordinatesExeption {
    private double x;
    private long y;

    public CoordinatesExeption x(double x) {
        if (x <= -552) {
            throw new IllegalArgumentException("X must be more than -552");
        }
        this.x = x;
        return this;


    }

    public CoordinatesExeption y(long y){
        this.y = Objects.requireNonNull(y, "Y cannot be null");
        if (y <= -921) {
            throw new IllegalArgumentException("Y must be more than -921");
        }
        return this;
    }

    public Coordinates newCoordinates(){
        return new Coordinates(x, y);
    }
}
