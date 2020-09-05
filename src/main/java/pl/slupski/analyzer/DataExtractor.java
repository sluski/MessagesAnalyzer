package pl.slupski.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DataExtractor {

    public static List<Message> extractMessages(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");
        return extractData(doc);
    }

    private static List<Message> extractData(Document doc) {
        List<Element> elements = doc.select(".pam");
        return elements.stream().map(element -> prepareMessage(element)).collect(Collectors.toList());
    }

    private static Message prepareMessage(Element element) {
        String[] splited = element.child(2).text().split(",");
        if (splited[2].trim().length() < 8) splited[2] = "0" + splited[2].trim();
        String day = splited[0].split(" ")[1];
        String date = (day.length() == 2 ? day : "0" + day) + "-" + monthNameToCalendar(splited[0].split(" ")[0]) + "-" + splited[1].trim();
        String time = to24Format(splited[2].trim());
        String user = element.child(0).text();
        String content = element.child(1).text();
        return new Message(user, date, time, content);
    }

    private static String to24Format(String time) {
        try {
            DateFormat f = new SimpleDateFormat("hh:mm aa");
            DateFormat fd = new SimpleDateFormat("HH:mm");
            Date d = f.parse(time);
            return fd.format(d);
        } catch (Exception ex) {
            return "";
        }
    }

    private static String monthNameToCalendar(String name) {
        if (name.equals("Jan")) {
            return "01";
        } else if (name.equals("Feb")) {
            return "02";
        } else if (name.equals("Mar")) {
            return "03";
        } else if (name.equals("Apr")) {
            return "04";
        } else if (name.equals("May")) {
            return "05";
        } else if (name.equals("Jun")) {
            return "06";
        } else if (name.equals("Jul")) {
            return "07";
        } else if (name.equals("Aug")) {
            return "08";
        } else if (name.equals("Sep")) {
            return "09";
        } else if (name.equals("Oct")) {
            return "10";
        } else if (name.equals("Nov")) {
            return "11";
        } else if (name.equals("Dec")) {
            return "12";
        }
        throw new InvalidParameterException("Value not valid: " + name);
    }


}