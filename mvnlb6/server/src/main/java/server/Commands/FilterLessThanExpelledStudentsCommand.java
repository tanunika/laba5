package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для фильтрации элементов с expelledStudents меньше заданного
 */
public class FilterLessThanExpelledStudentsCommand implements Commands {
    private final CollectionManager collectionManager;

    public FilterLessThanExpelledStudentsCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "filter_less_than_expelled_students";
    }

    @Override
    public String execute(String arguments) {
        try {
            long expelledStudents = Long.parseLong(arguments.trim());
            return execute(expelledStudents);
        } catch (NumberFormatException e) {
            return "Неверный формат числа. Должно быть целое число.";
        }
    }

    public String execute(long expelledStudents) {
        StringBuilder result = new StringBuilder();
        collectionManager.getCollection().stream()
                .filter(g -> g.getExpelledStudents() < expelledStudents)
                .forEach(g -> result.append(g).append("\n"));

        if (result.length() == 0) {
            return "Нет элементов, удовлетворяющих условию.";
        }
        return result.toString();
    }
}