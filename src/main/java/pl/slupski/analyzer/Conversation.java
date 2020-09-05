package pl.slupski.analyzer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Conversation {

    private final String id;
    private final ConversationType type;
    private final String recipient;
    private List<Message> messages;

    public Conversation(String id, String recipient, ConversationType type) {
        this.id = id;
        this.recipient = recipient;
        this.type = type;
        this.messages = new ArrayList<>();
    }
}
