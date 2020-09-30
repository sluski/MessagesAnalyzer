package pl.slupski.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.slupski.analyzer.execption.FileContentException;
import pl.slupski.analyzer.execption.UnsupportedLanguageException;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class DataExtractor {

    private Map<String, File> recipients = new HashMap<>(); // key - recipient name, value - recipient dictionary

    private final static String HTML_FILE_TYPE = ".html";
    private final static String FILE_ENCODING = "UTF-8";
    private final static String RECIPIENT_CSS_CLASS = "._2lek";
    private final static String HOME_BUTTON_CSS_CLASS = "._2s24";
    private final static String EN_HOME_BUTTON_TEXT = "Home";
    private final static String PL_HOME_BUTTON_TEXT = "Strona główna";
    private final static String EN = "en";
    private final static String PL = "pl";
    private final static String HREF_ATTRIBUTE = "href";
    private final static String SLASH = "/";

    public DataExtractor(File file) {
        try {
            Document mainFileDocument = Jsoup.parse(file, FILE_ENCODING);
            if (file.exists() && file.getName().endsWith(HTML_FILE_TYPE)) {
                Elements elements = mainFileDocument.select(RECIPIENT_CSS_CLASS);
                for (Element element : elements) {
                    String recipient = element.text();
                    File recipientDictionary = new File(file.getParentFile().getParent() + SLASH + splitToParentPath(element.children().get(0).attr(HREF_ATTRIBUTE)));
                    recipients.put(recipient, recipientDictionary);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Conversation> prepareConversations() {
        try {
            List<Conversation> result = new ArrayList<>();
            for (Map.Entry<String, File> recipient : recipients.entrySet()) {
                result.add(prepareConversation(recipient.getKey(), recipient.getValue()));
            }
            return result;
        } catch (IOException ex) {
            // todo: throw error
            throw new RuntimeException("AA");
        }
    }

    private Conversation prepareConversation(String recipient, File dictionary) throws IOException {
        String id = UUID.randomUUID().toString();
        ConversationType type = null;
        List<Message> messages = new ArrayList<>();
        for (File file : findHtmlFiles(dictionary)) {
            Document document = Jsoup.parse(file, FILE_ENCODING);
            if (Objects.isNull(type)) {
                type = extractConversationType(document);
            }
            messages.addAll(extractMessages(document));
        }
        Map<Integer, Year> years = prepareTimeTree(messages);
        return new Conversation(id, type, recipient, years);
    }

    private Map<Integer, Year> prepareTimeTree(List<Message> messages) {
        Map<Integer, Year> yearsMap = new HashMap<>();
        Map<Integer, Month> monthsMap = new HashMap<>();
        Map<Integer, Day> daysMap = new HashMap<>();
        for (Message message : messages) {
            if (!daysMap.containsKey(message.getDay())) {
                Day day = new Day(message.getDay());
                day.getMessages().add(message);
                daysMap.put(message.getDay(), day);
                if (!monthsMap.containsKey(message.getMonth())) {
                    Month month = new Month(message.getMonth());
                    month.getDays().put(day.getDayOfMonth(), day);
                    monthsMap.put(message.getMonth(), month);
                    if (!yearsMap.containsKey(message.getYear())) {
                        Year year = new Year(message.getYear());
                        year.getMonths().put(month.getMonthOfYear(), month);
                        yearsMap.put(message.getYear(), year);
                    } else {
                        yearsMap.get(message.getYear()).getMonths().put(month.getMonthOfYear(), month);
                    }
                } else {
                    monthsMap.get(message.getMonth()).getDays().put(day.getDayOfMonth(), day);
                }
            } else {
                daysMap.get(message.getDay()).getMessages().add(message);
            }
        }
        return yearsMap;
    }

    private List<File> findHtmlFiles(File dictionary) {
        return Arrays.asList(dictionary.listFiles()).stream().filter(file -> file.getName().endsWith(HTML_FILE_TYPE)).collect(Collectors.toList());
    }

    private String splitToParentPath(String path) {
        return path.substring(0, path.lastIndexOf(SLASH));
    }

    public ConversationType extractConversationType(Document doc) {
        return doc.select(".pam").get(0).children().size() == 3 ? ConversationType.DIRECT : ConversationType.GROUP; //if first element contains only two children its mean its group
    }

    private List<Message> extractMessages(Document doc) {
        String language = findLanguage(doc);
        List<Element> elements = doc.select(".pam");
        if (elements.get(0).text().contains(",")) elements = elements.subList(1, elements.size());
        return elements.stream().map(element -> prepareMessage(element, language)).collect(Collectors.toList());
    }

    private String findLanguage(Document doc) {
        String home = doc.select(HOME_BUTTON_CSS_CLASS).get(0).text();
        if (home.trim().equals(EN_HOME_BUTTON_TEXT)) return EN;
        else if (home.trim().equals(PL_HOME_BUTTON_TEXT)) return PL;
        return "unsupported"; // todo: throw not known language error
    }

    private Message prepareMessage(Element element, String lang) {
        try {
            String tempDate;
            tempDate = RegionalDateConverter.convertDate(element.child(2).text(), lang);
            String user = element.child(0).text();
            String content = element.child(1).text();
            return new Message(user, tempDate.split(" ")[0], tempDate.split(" ")[1], content);
        } catch (ParseException ex) {
            throw new UnsupportedLanguageException("error"); // todo: do zmiany
        }
    }


}
