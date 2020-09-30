package pl.slupski.analyzer;

import org.springframework.stereotype.Service;
import pl.slupski.analyzer.execption.InvalidDirectoryPathException;

import java.io.File;
import java.util.*;

@Service
public class AnalyzerService {

    private List<Conversation> conversations;

    public void init(File file) {
        DataExtractor extractor = new DataExtractor(file);
        conversations = extractor.prepareConversations();
    }

    public List<Conversation> findConversations() {
        return conversations;
    }

    public List<Recipient> findAllRecipients() {
        List<Recipient> result = new ArrayList<>();
        conversations.forEach(conversation ->
                result.add(new Recipient(conversation.getId(), conversation.getRecipient(), conversation.getType()))
        );
        return result;
    }

}