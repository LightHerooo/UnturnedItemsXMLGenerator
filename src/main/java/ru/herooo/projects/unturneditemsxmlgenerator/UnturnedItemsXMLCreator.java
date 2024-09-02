package ru.herooo.projects.unturneditemsxmlgenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ru.herooo.projects.unturneditemsxmlgenerator.pojo.UnturnedItem;
import ru.herooo.projects.unturneditemsxmlgenerator.pojo.UnturnedItemTable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UnturnedItemsXMLCreator {
    public static File create(UnturnedItemTable unturnedItemTable, LocalDateTime localDateTime) throws ParserConfigurationException, TransformerException, IOException {
        // Генерируем данные для XML-файла
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Node root = document.createElement("UnturnedItems");
        document.appendChild(root);

        Node itemsNode = document.createElement("Items");
        root.appendChild(itemsNode);

        for (UnturnedItem unturnedItem: unturnedItemTable.getUnturnedItems()) {
            Element itemElement = document.createElement("Item");
            itemsNode.appendChild(itemElement);

            itemElement.setAttribute("id", String.valueOf(unturnedItem.getId()));
            itemElement.setAttribute("name", unturnedItem.getName());
        }

        // Генерируем XML-файл на основе данных
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File xml = createNewXMLFile(unturnedItemTable.getName(), localDateTime);
        StreamResult streamResult = new StreamResult(xml);
        transformer.transform(source, streamResult);

        return xml;
    }

    private static File createNewXMLFile(String name, LocalDateTime localDateTime) throws IOException {
        File directory = new File(String.format("results/%s",
                localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy (hh-mm-ss)"))));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(String.format("%s/%s.xml",
                directory.getPath(),
                name));
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}
