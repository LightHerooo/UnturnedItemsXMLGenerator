package ru.herooo.projects.unturneditemsxmlgenerator;

import org.jsoup.select.Elements;
import ru.herooo.projects.unturneditemsxmlgenerator.pojo.UnturnedItemTable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class UnturnedItemsXMLGenerator {
    public static void main(String[] args) {
        // Получаем таблицы предметов
        Elements tables = null;
        try {
            tables = UnturnedHTMLReader.readTables();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Произошла ошибка парсинга (%s)", e.getMessage()));
        }

        // Проверяем количество полученных таблиц предметов
        if (tables == null || tables.size() == 0) {
            System.out.println("Элементы не найдены");
            return;
        }

        // Генерируем XML-файлы
        LocalDateTime now = LocalDateTime.now();
        UnturnedItemTable firstUnturnedItemTable = new UnturnedItemTable(tables.get(0));
        for (int i = 0; i < tables.size(); i++) {
            UnturnedItemTable unturnedItemTable = null;
            if (i != 0) {
                unturnedItemTable = new UnturnedItemTable(tables.get(i));
                if (unturnedItemTable.getName().equals(firstUnturnedItemTable.getName())) break;
            } else {
                unturnedItemTable = firstUnturnedItemTable;
            }

            System.out.println("---------------");
            System.out.printf("Идёт генерация XML-файла \"%s\"\n", unturnedItemTable.getName());
            System.out.println("---------------");
            try {
                File xml = UnturnedItemsXMLCreator.create(unturnedItemTable, now);
                System.out.printf("Файл \"%s\" успешно сгенерирован!\n", xml.getName());
                System.out.printf("Путь к файлу: %s\n", xml.getAbsolutePath());
                System.out.println("---------------");
                System.out.println();
            } catch (ParserConfigurationException | TransformerException | IOException e) {
                System.out.println("---------------");
                throw new RuntimeException(String.format("Произошла ошибка генерации XML-файла \"%s\" (%s)",
                        unturnedItemTable.getName(),
                        e.getMessage()));
            }
        }
    }
}
