package Exeptions;

import StudyGroup.Location;

public class LocationExeption {
    private float x;
    private float y; //Поле не может быть null
    private float z;

    public LocationExeption y(float y) {
        this.y = y;
        return this;
    }

    public LocationExeption x(float x) {
        this.x = x;
        return this;
    }

    public LocationExeption z(float z) {
        this.z = z;
        return this;
    }

    public Location newLocation(){
        return new Location(x, y, z);
    }
}
