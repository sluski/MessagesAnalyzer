package pl.slupski.analyzer;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class AnalyzerService {

    private final List<Conversation> conversations = new ArrayList<>();

    
    public void init(File file) throws IOException, ParseException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                Conversation conversation = DataExtractor.extractConversation(f);
                if(Objects.nonNull(conversation)) conversations.add(conversation);
            }
        }
    }

    public List<Recipient> findAllRecipients() {
        List<Recipient> result = new ArrayList<>();
        conversations.forEach(conversation ->
            result.add(new Recipient(conversation.getId(), conversation.getRecipient(), conversation.getType(), conversation.getMessages().size()))
        );
        return result;
    }
}
