package StudyGroup;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudyGroup {
    private static int LastId = 0; //для автоматической генерации ID

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private long expelledStudents; //Значение поля должно быть больше 0
    private long shouldBeExpelled; //Значение поля должно быть больше 0
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле может быть null

    public StudyGroup() {
    }

    public StudyGroup(String name,
                      Coordinates coordinates,
                      LocalDateTime creationDate, long studentsCount,
                      long expelledStudents,
                      long shouldBeExpelled,
                      Semester semesterEnum,
                      Person groupAdmin) {

        //автоматическая генерация полей
        this.id = ++LastId;
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = Objects.requireNonNull(coordinates, "Coordinates cannot be null");
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.shouldBeExpelled = shouldBeExpelled;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public StudyGroup(String name, int studentsCount) {
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public Coordinates getCoordinates() {return coordinates;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public long getStudentsCount() {return this.studentsCount;}
    public long getExpelledStudents() {return expelledStudents;}
    public long getShouldBeExpelled() {return shouldBeExpelled;}
    public Semester getSemesterEnum() {return semesterEnum;}
    public Person getGroupAdmin() {return groupAdmin;}

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", expelledStudents=" + expelledStudents +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin +
                '}';

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = Objects.requireNonNull(coordinates, "Coordinates cannot be null");
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = Objects.requireNonNull(creationDate, "Creation date cannot be null");
    }

    public static void resetLastId() {
        LastId = 0;
    }

    public void setStudentsCount(int studentsCount) {
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("Значение studentsCount должно быть больше 0");
        }
        this.studentsCount = studentsCount;
    }
}