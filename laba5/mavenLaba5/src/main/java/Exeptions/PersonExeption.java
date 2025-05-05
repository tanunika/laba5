package Exeptions;

import StudyGroup.Location;
import StudyGroup.Person;
import StudyGroup.eyeColor.Color;
import StudyGroup.hairColor.ColorHair;

public class PersonExeption {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private ColorHair hairColor; //Поле не может быть null
    private Location location;

    public PersonExeption name(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
        return this;
    }

    public PersonExeption passportID(String passportID) {
        if (passportID == null) {
            throw new IllegalArgumentException("passportID cannot be null");
        }
        this.passportID = passportID;
        return this;
    }

    public PersonExeption hairColor(ColorHair hairColor){
        if (hairColor == null){
            throw new IllegalArgumentException("hairColor cannot be null");
        }
        this.hairColor = hairColor;
        return this;
    }

    public Person newPerson(){
        return new Person(name, passportID, eyeColor, hairColor, location);
    }
}

