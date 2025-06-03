package server.utils;

import shared.Structures.StudyGroup;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Менеджер коллекции, отвечающий за хранение и управление коллекцией StudyGroup
 */
public class CollectionManager {
    private LinkedList<StudyGroup> collection;
    private LocalDate initializationDate;
    private String fileName;

    public CollectionManager(String fileName) {
        this.collection = new LinkedList<>();
        this.initializationDate = LocalDate.now();
        this.fileName = fileName;
        loadFromFile();
    }

    /**
     * Загружает коллекцию из файла
     */
    private void loadFromFile() {
        try {
            XMLParser parser = new XMLParser();
            this.collection = parser.parse(fileName);
            Collections.sort(collection); // Сортировка по умолчанию
        } catch (Exception e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
        }
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveToFile() {
        try {
            XMLParser parser = new XMLParser();
            parser.write(collection, fileName);
        } catch (Exception e) {
            System.err.println("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    public LinkedList<StudyGroup> getCollection() {
        return collection;
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public String getInfo() {
        return "Тип коллекции: " + collection.getClass().getSimpleName() + "\n" +
                "Дата инициализации: " + initializationDate + "\n" +
                "Количество элементов: " + collection.size();
    }


}