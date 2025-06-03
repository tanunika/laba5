package shared.utils;

public class StudyGroupCounter {
    private static final StudyGroupCounter instance = new StudyGroupCounter();
    int count = 0;

    private StudyGroupCounter() {
        // приватный конструктор — никто не может создать новый экземпляр
    }

    public static StudyGroupCounter getInstance() {
        return instance;
    }

    public int getCount() {
        return count;
    }
    public void increment(){
        count++;
    }
    public void setCount(int i){
        count = i;
    }
}