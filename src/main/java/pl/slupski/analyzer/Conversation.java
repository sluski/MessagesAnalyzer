package pl.slupski.analyzer;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
public class Conversation {

    private final String id;
    private final ConversationType type;
    private final String recipient;
    private final Map<Long, Day> days = new HashMap<>();

    public Conversation(String id, String recipient, ConversationType type) throws ParseException {
        this.id = id;
        this.recipient = recipient;
        this.type = type;
    }

    public void initDays(List<Message> messages) throws ParseException {
        for (Message message : messages) {
            Long number = prepareDateKey(message.getDate());
            Day day = days.get(number);
            if(Objects.isNull(day)) {
                day = new Day(number);
                days.put(number, day);
            }
            day.addMessage(message);
        }
    }

    private long prepareDateKey(String date) throws ParseException {
        Date d = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        LocalDate dateBefore = LocalDate.parse("1970-01-01");
        LocalDate dateAfter = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(d));
        return ChronoUnit.DAYS.between(dateBefore, dateAfter);
    }
}
