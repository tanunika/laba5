package Exeptions;

import StudyGroup.Coordinates;
import StudyGroup.Person;
import StudyGroup.Semester;
import StudyGroup.StudyGroup;

import java.time.LocalDateTime;

public class StudyGroupExeption {
    private static int LastId = 0;
    private int id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private long studentsCount;
    private long expelledStudents;
    private long shouldBeExpelled;
    private Semester semesterEnum;
    private Person groupAdmin;

    public StudyGroupExeption(){
        this.id = ++LastId;
        this.creationDate = LocalDateTime.now();
    }

    public StudyGroupExeption name(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
        return this;
    }

    public StudyGroupExeption coordinates(Coordinates coordinates){
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
        return this;
    }

    public StudyGroupExeption studentsCount (long studentsCount) {
        if (studentsCount <= 0){
            throw new IllegalArgumentException("studentCount must be more than 0");
        }
        this.studentsCount = studentsCount;
        return this;
    }

    public StudyGroupExeption expelledStudents(long expelledStudents) {
        if (expelledStudents <= 0) {
            throw new IllegalArgumentException("expelledStudents must be more than 0");
        }

        this.expelledStudents = expelledStudents;
        return this;
    }

    public StudyGroupExeption shouldBeExpelled(long shouldBeExpelled) {
        if (shouldBeExpelled <= 0) {
            throw new IllegalArgumentException("shouldBeExpelled must be more than 0");
        }

        this.shouldBeExpelled = shouldBeExpelled;
        return this;
    }


    public StudyGroup newGroup(){
        return new StudyGroup(name, coordinates, creationDate, studentsCount, expelledStudents,
                shouldBeExpelled, semesterEnum, groupAdmin);

    }

}
