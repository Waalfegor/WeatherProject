package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // \d{2} \. \d{2}
    // берём 2 символа точку и ещё 2 символа
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        //css query language
        Element tableWht = page.select("table[class=wt]").first();
        Elements names = tableWht.select("tr[class=wth]");
        Elements values = tableWht.select("tr[valign=top]");
        int index = 0;
        for (Element name: names) {
            String stringDate=name.select("th[id=dt]").text();
            String date = getDateFromString(stringDate);
            System.out.println(date + "    Явления    Температура    Давленииие    Влажность    Ветер");
            index += printValues(values,index);
        }

    }
    private static Document getPage() throws IOException {
        String url="http://www.pogoda.spb.ru/";
        return Jsoup.parse(new URL(url), 3000);
    }
    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()){
            return matcher.group();
        }
        throw new Exception("Cant extract date from string");
    }

    private static int printValues(Elements values, int index) {
        int iterationCount = 4;

        if (index == 0) {
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            boolean isDay  = valueLn.text().contains("День");
            boolean isEvening  = valueLn.text().contains("Вечер");
            if (isMorning) {
                iterationCount = 3;
            }
            if (isDay) {
                iterationCount = 2;
            }
            if (isEvening) {
                iterationCount = 1;
            }
        }

        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "    ");
            }
            System.out.println();
        }
        return iterationCount;
    }
}