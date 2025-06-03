package server.utils;

import shared.Structures.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedList;

public class XMLParser {

    public LinkedList<StudyGroup> parse(String fileName) throws Exception {
        LinkedList<StudyGroup> groups = new LinkedList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));

            NodeList groupNodes = doc.getElementsByTagName("studyGroup");

            for (int i = 0; i < groupNodes.getLength(); i++) {
                Node node = groupNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    groups.add(parseStudyGroup(element));
                }
            }
        } catch (Exception e) {
            throw new Exception("Ошибка парсинга XML: " + e.getMessage());
        }
        return groups;
    }


    public void write(LinkedList<StudyGroup> groups, String fileName) throws Exception {
        // Нормализация пути к файлу
        fileName = fileName.trim();
        File file = new File(fileName).getAbsoluteFile();
        File tempFile = new File(file.getParentFile(), file.getName() + ".tmp");

        try {
            // Создаем директории, если их нет
            file.getParentFile().mkdirs();

            // Создаем XML документ
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Создаем корневой элемент
            Element rootElement = doc.createElement("studyGroups");
            doc.appendChild(rootElement);

            // Добавляем все группы в документ
            for (StudyGroup group : groups) {
                Element groupElement = doc.createElement("studyGroup");

                // Заполняем основные поля
                addTextElement(doc, groupElement, "id", String.valueOf(group.getId()));
                addTextElement(doc, groupElement, "name", group.getName());
                addTextElement(doc, groupElement, "creationDate", group.getCreationDate().toString());
                addTextElement(doc, groupElement, "studentsCount", String.valueOf(group.getStudentsCount()));
                addTextElement(doc, groupElement, "expelledStudents", String.valueOf(group.getExpelledStudents()));
                addTextElement(doc, groupElement, "shouldBeExpelled", String.valueOf(group.getShouldBeExpelled()));

                // Добавляем координаты
                Element coordinatesElement = doc.createElement("coordinates");
                addTextElement(doc, coordinatesElement, "x", String.valueOf(group.getCoordinates().getX()));
                addTextElement(doc, coordinatesElement, "y", String.valueOf(group.getCoordinates().getY()));
                groupElement.appendChild(coordinatesElement);

                // Добавляем семестр (если есть)
                if (group.getSemester() != null) {
                    addTextElement(doc, groupElement, "semester", group.getSemester().name());
                }

                // Добавляем администратора группы (если есть)
                if (group.getGroupAdmin() != null) {
                    Element adminElement = doc.createElement("groupAdmin");
                    addTextElement(doc, adminElement, "name", group.getGroupAdmin().getName());
                    addTextElement(doc, adminElement, "passportID", group.getGroupAdmin().getPassportID());

                    if (group.getGroupAdmin().getEyeColor() != null) {
                        addTextElement(doc, adminElement, "eyeColor", group.getGroupAdmin().getEyeColor().name());
                    }

                    addTextElement(doc, adminElement, "hairColor", group.getGroupAdmin().getHairColor().name());

                    if (group.getGroupAdmin().getLocation() != null) {
                        Element locationElement = doc.createElement("location");
                        addTextElement(doc, locationElement, "x", String.valueOf(group.getGroupAdmin().getLocation().getX()));
                        addTextElement(doc, locationElement, "y", String.valueOf(group.getGroupAdmin().getLocation().getY()));
                        addTextElement(doc, locationElement, "z", String.valueOf(group.getGroupAdmin().getLocation().getZ()));
                        adminElement.appendChild(locationElement);
                    }

                    groupElement.appendChild(adminElement);
                }

                rootElement.appendChild(groupElement);
            }

            // Настраиваем преобразователь для красивого форматирования
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Записываем во временный файл
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                transformer.transform(new DOMSource(doc), new StreamResult(fos));
            }

            // Заменяем оригинальный файл
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Не удалось удалить старый файл");
                }
            }

            if (!tempFile.renameTo(file)) {
                throw new IOException("Не удалось переименовать временный файл");
            }

        } catch (Exception e) {
            // Удаляем временный файл в случае ошибки
            if (tempFile.exists()) {
                tempFile.delete();
            }
            throw new Exception("Ошибка сохранения коллекции в файл: " + e.getMessage(), e);
        }
    }

    private StudyGroup parseStudyGroup(Element element) {
        int id = Integer.parseInt(getElementText(element, "id"));
        String name = getElementText(element, "name");
        Coordinates coordinates = parseCoordinates((Element)element.getElementsByTagName("coordinates").item(0));
        LocalDate creationDate = LocalDate.parse(getElementText(element, "creationDate"));
        long studentsCount = Long.parseLong(getElementText(element, "studentsCount"));
        long expelledStudents = Long.parseLong(getElementText(element, "expelledStudents"));
        long shouldBeExpelled = Long.parseLong(getElementText(element, "shouldBeExpelled"));

        Semester semester = null;
        if (element.getElementsByTagName("semester").getLength() > 0) {
            semester = Semester.valueOf(getElementText(element, "semester"));
        }

        Person groupAdmin = null;
        if (element.getElementsByTagName("groupAdmin").getLength() > 0) {
            groupAdmin = parsePerson((Element)element.getElementsByTagName("groupAdmin").item(0));
        }

        StudyGroup group = new StudyGroup(name, coordinates, studentsCount,
                expelledStudents, shouldBeExpelled, semester, groupAdmin);
        group.setId(id);
        // Устанавливаем creationDate через конструктор или рефлексию, так как сеттера нет
        // Временное решение - использовать рефлексию
        try {
            Field creationDateField = StudyGroup.class.getDeclaredField("creationDate");
            creationDateField.setAccessible(true);
            creationDateField.set(group, creationDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set creation date", e);
        }
        return group;
    }

    private Element createStudyGroupElement(Document doc, StudyGroup group) {
        Element groupElement = doc.createElement("studyGroup");

        addTextElement(doc, groupElement, "id", String.valueOf(group.getId()));
        addTextElement(doc, groupElement, "name", group.getName());
        addTextElement(doc, groupElement, "creationDate", group.getCreationDate().toString());
        addTextElement(doc, groupElement, "studentsCount", String.valueOf(group.getStudentsCount()));
        addTextElement(doc, groupElement, "expelledStudents", String.valueOf(group.getExpelledStudents()));
        addTextElement(doc, groupElement, "shouldBeExpelled", String.valueOf(group.getShouldBeExpelled()));

        groupElement.appendChild(createCoordinatesElement(doc, group.getCoordinates()));

        if (group.getSemester() != null) {
            addTextElement(doc, groupElement, "semester", group.getSemester().name());
        }

        if (group.getGroupAdmin() != null) {
            groupElement.appendChild(createPersonElement(doc, group.getGroupAdmin()));
        }

        return groupElement;
    }

    private Person parsePerson(Element personElement) {
        String name = getElementText(personElement, "name");
        String passportID = getElementText(personElement, "passportID");

        Color eyeColor = null;
        if (personElement.getElementsByTagName("eyeColor").getLength() > 0) {
            eyeColor = Color.valueOf(getElementText(personElement, "eyeColor"));
        }

        ColorHair hairColor = ColorHair.valueOf(getElementText(personElement, "hairColor"));

        Location location = null;
        if (personElement.getElementsByTagName("location").getLength() > 0) {
            location = parseLocation((Element)personElement.getElementsByTagName("location").item(0));
        }

        return new Person(name, passportID, eyeColor, hairColor, location);
    }

    private Element createPersonElement(Document doc, Person person) {
        Element personElement = doc.createElement("groupAdmin");

        addTextElement(doc, personElement, "name", person.getName());
        addTextElement(doc, personElement, "passportID", person.getPassportID());

        if (person.getEyeColor() != null) {
            addTextElement(doc, personElement, "eyeColor", person.getEyeColor().name());
        }

        addTextElement(doc, personElement, "hairColor", person.getHairColor().name());

        if (person.getLocation() != null) {
            personElement.appendChild(createLocationElement(doc, person.getLocation()));
        }

        return personElement;
    }

    private Coordinates parseCoordinates(Element coordElement) {
        double x = Double.parseDouble(getElementText(coordElement, "x"));
        double y = Double.parseDouble(getElementText(coordElement, "y"));
        return new Coordinates(x, y);
    }

    private Element createCoordinatesElement(Document doc, Coordinates coord) {
        Element coordElement = doc.createElement("coordinates");
        addTextElement(doc, coordElement, "x", String.valueOf(coord.getX()));
        addTextElement(doc, coordElement, "y", String.valueOf(coord.getY()));
        return coordElement;
    }

    private Location parseLocation(Element locationElement) {
        double x = Double.parseDouble(getElementText(locationElement, "x"));
        double y = Double.parseDouble(getElementText(locationElement, "y"));
        double z = Double.parseDouble(getElementText(locationElement, "z"));
        return new Location(x, y, z);
    }

    private Element createLocationElement(Document doc, Location location) {
        Element locationElement = doc.createElement("location");
        addTextElement(doc, locationElement, "x", String.valueOf(location.getX()));
        addTextElement(doc, locationElement, "y", String.valueOf(location.getY()));
        addTextElement(doc, locationElement, "z", String.valueOf(location.getZ()));
        return locationElement;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return "";
        return nodes.item(0).getTextContent();
    }

    private void addTextElement(Document doc, Element parent, String name, String value) {
        if (value != null) {
            Element element = doc.createElement(name);
            element.appendChild(doc.createTextNode(value));
            parent.appendChild(element);
        }
    }
}