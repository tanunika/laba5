package shared.Structures;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private static int nextId = 1;

    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long studentsCount;
    private long expelledStudents;
    private long shouldBeExpelled;
    private Semester semester;
    private Person groupAdmin;

    // Конструктор
    public StudyGroup(String name, Coordinates coordinates, long studentsCount,
                      long expelledStudents, long shouldBeExpelled, Semester semester,
                      Person groupAdmin) {
        this.id = nextId++;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.coordinates = Objects.requireNonNull(coordinates, "Coordinates cannot be null");
        this.creationDate = LocalDate.now();
        setStudentsCount(studentsCount);
        setExpelledStudents(expelledStudents);
        setShouldBeExpelled(shouldBeExpelled);
        this.semester = semester;
        this.groupAdmin = groupAdmin;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public long getExpelledStudents() {
        return expelledStudents;
    }

    public long getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public Semester getSemester() {
        return semester;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    // Сеттеры с валидацией
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
    }

    public void setStudentsCount(long studentsCount) {
        if (studentsCount <= 0) throw new IllegalArgumentException("Students count must be greater than 0");
        this.studentsCount = studentsCount;
    }

    public void setExpelledStudents(long expelledStudents) {
        if (expelledStudents <= 0) throw new IllegalArgumentException("Expelled students must be greater than 0");
        this.expelledStudents = expelledStudents;
    }

    public void setShouldBeExpelled(long shouldBeExpelled) {
        if (shouldBeExpelled <= 0) throw new IllegalArgumentException("Should be expelled must be greater than 0");
        this.shouldBeExpelled = shouldBeExpelled;
    }    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = Objects.requireNonNull(creationDate, "Creation date cannot be null");
    }

    // Реализация Comparable
    @Override
    public int compareTo(StudyGroup other) {
        return Long.compare(this.studentsCount, other.studentsCount);
    }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
        // Обновляем nextId, если переданный ID больше текущего nextId
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
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
                ", semester=" + semester +
                ", groupAdmin=" + groupAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup that = (StudyGroup) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}