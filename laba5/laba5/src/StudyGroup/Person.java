package StudyGroup;

import StudyGroup.eyeColor.Color;
import StudyGroup.hairColor.ColorHair;

import StudyGroup.Location;


public class Person {
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private final Color eyeColor; //Поле может быть null
    private final ColorHair hairColor; //Поле не может быть null
    private final Location location; //Поле может быть null

    public Person(String name,
                  String passportID,
                  Color eyeColor,
                  ColorHair hairColor,
                  Location location) {

        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
    }
    public  String getName(){return name;}
    public String getPassportID(){return passportID;}
    public Color getEyeColor(){return eyeColor;}
    public ColorHair getHairColor(){return hairColor;}
    public Location getLocation(){return location;}

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", passportID='" + passportID + '\'' +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", location=" + location +
                '}';

    }
}
