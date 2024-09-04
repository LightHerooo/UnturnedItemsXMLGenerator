# UnturnedItemsXMLGenerator
Генератор XML-файлов предметов игры Unturned

## Стэк
- Java 21 ([JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html))
- JSoup 1.17.1 ([Сайт](https://jsoup.org/) | [Git](https://github.com/jhy/jsoup) | [Maven](https://mvnrepository.com/artifact/org.jsoup/jsoup/1.17.1))

## Установка
1. Скачайте [ZIP](https://disk.yandex.ru/d/Tjm7wF1CTx3cOg)
2. Распакуйте архив

## Использование
Запустите файл `run.bat`

Сгенерированные файлы будут находиться по пути `/results/ДЕНЬ.МЕСЯЦ.ГОД (ЧАСЫ-МИНУТЫ-СЕКУНДЫ)`. Директория `/results` появится в директории, где находится `UnturnedItemsXMLGenerator.jar`.

## Как это работает?
При запуске `run.bat` начинается парсинг [этой страницы](https://unturned.fandom.com/wiki/ID_List). Из неё извлекаются все таблицы и делятся на отдельные XML-файлы. Название каждого сгенерированного XML-файла соответствует названию каждой таблицы.

Структура каждого XML-файла одинакова и выглядит следующим образом:
```XML
<UnturnedItems>
    <Items>
        <Item id="2" name="Work Jeans"/>
        <Item id="3" name="Orange Hoodie"/>
        <!-- ... -->
    </Items>
<UnturnedItems>
```
