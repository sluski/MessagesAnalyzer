package pl.slupski.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataExtractor {

    public static Conversation extractConversation(File file) throws IOException, ParseException {
        Conversation result = null;
        Document doc;
        for (File f : file.listFiles()) {
            if (f.getName().endsWith(".html")) {
                doc = Jsoup.parse(f, "UTF-8");
                String recipient = doc.select("._3b0c").get(0).text();
                ConversationType type = checkConversationType(doc);
                result = new Conversation(UUID.randomUUID().toString(), recipient, type);
                result.getMessages().addAll(extractData(doc));
                result.initDays();
            }
        }
        return result;
    }

    private static ConversationType checkConversationType(Document doc) {
        return doc.select(".pam").get(0).children().size() == 3 ? ConversationType.DIRECT : ConversationType.GROUP; //if first element contains only two children its mean its group
    }

    private static List<Message> extractData(Document doc) {
        String language = findLanguage(doc);
        List<Element> elements = doc.select(".pam");
        if (elements.get(0).text().contains(",")) elements = elements.subList(1, elements.size());
        return elements.stream().map(element -> prepareMessage(element, language)).collect(Collectors.toList());
    }

    private static String findLanguage(Document doc) {
        String home = doc.select("._2s24").get(0).text();
        if (home.trim().equals("Home")) return "en";
        else if (home.trim().equals("Strona główna")) return "pl";
        return "unsupported";
    }

    private static Message prepareMessage(Element element, String lang) {
        try {
            String tempDate;
            tempDate = RegionalDateConverter.convertDate(element.child(2).text(), lang);
            String user = element.child(0).text();
            String content = element.child(1).text();
            return new Message(user, tempDate.split(" ")[0], tempDate.split(" ")[1], content);
        } catch (ParseException ex) {
            throw new UnsupportedLanaguage("error"); // todo: do zmiany
        }
    }


}
