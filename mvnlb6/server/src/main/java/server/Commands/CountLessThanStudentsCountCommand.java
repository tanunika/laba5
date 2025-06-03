package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для подсчета элементов с studentsCount меньше заданного
 */
public class CountLessThanStudentsCountCommand implements Commands {
    private final CollectionManager collectionManager;

    public CountLessThanStudentsCountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "count_less_than_students_count";
    }

    @Override
    public String execute(String arguments) {
        try {
            long studentsCount = Long.parseLong(arguments.trim());
            return execute(studentsCount);
        } catch (NumberFormatException e) {
            return "Неверный формат числа. Должно быть целое число.";
        }
    }

    public String execute(long studentsCount) {
        long count = collectionManager.getCollection().stream()
                .filter(g -> g.getStudentsCount() < studentsCount)
                .count();
        return "Количество элементов: " + count;
    }
}