package pl.slupski.analyzer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Day {
    private final long dateId;
    private final List<Message> messages;

    public Day(long dateId) {
        this.dateId = dateId;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}