package ru.herooo.projects.unturneditemsxmlgenerator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UnturnedHTMLReader {
    public static Elements readTables() throws IOException {
        // Получаем .html с ресурса
        Document document = Jsoup.connect("https://unturned.fandom.com/wiki/ID_List")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

        // Ищем таблицы
        return document.select("table");
    }
}
