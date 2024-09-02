package ru.herooo.projects.unturneditemsxmlgenerator.pojo;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.herooo.projects.unturneditemsxmlgenerator.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UnturnedItemTable {
    private String name = null;
    private List<UnturnedItem> unturnedItems = new ArrayList<>();

    public UnturnedItemTable(Element table) {
        if (table != null) {
            Element tdName = table.selectFirst(".tables-m3");
            if (tdName != null) {
                String name = tdName.text();

                int openBracketIndex = name.indexOf('(');
                if (openBracketIndex != -1) {
                    this.name = name.substring(0, openBracketIndex).trim();
                }
            }

            Elements rows = table.select("tr");
            if (rows != null) {
                for (Element row : rows) {
                    Elements cells = row.select("td");
                    List<UnturnedItem> partToUnturnedItems = tryToCreatePartToUnturnedItems(cells);
                    if (partToUnturnedItems != null) {
                        unturnedItems.addAll(partToUnturnedItems);
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<UnturnedItem> getUnturnedItems() {
        return unturnedItems;
    }

    private List<UnturnedItem> tryToCreatePartToUnturnedItems(Elements cells) {
        List<UnturnedItem> unturnedItems = null;
        if (cells != null && cells.size() == 6) {
            unturnedItems = new ArrayList<>();

            for (int i = 0; i < cells.size(); i++) {
                Element nameCell = cells.get(i);
                if (++i >= cells.size()) break;
                Element idCell = cells.get(i);

                UnturnedItem unturnedItem = tryToCreateUnturnedItem(idCell, nameCell);
                if (unturnedItem != null) {
                    unturnedItems.add(unturnedItem);
                }
            }
        }

        return unturnedItems;
    }

    private UnturnedItem tryToCreateUnturnedItem(Element idCell, Element nameCell) {
        if (idCell == null || nameCell == null) return null;

        String idStr = idCell.text();
        String name = nameCell.text();
        if (StringUtils.isNull(idStr) || StringUtils.isNull(name)) return null;

        // Id должен быть больше нуля
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id <= 0) return null;
        } catch (NumberFormatException e) {
            return null;
        }

        // name должен быть определённым
        if (name.equalsIgnoreCase("n/a")) return null;

        return new UnturnedItem(id, name);
    }
}
