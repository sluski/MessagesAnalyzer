package pl.slupski.analyzer;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerService {

    private List<Conversation> conversations = new ArrayList<>();

    public void init(File file) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) conversations.add(DataExtractor.extractConversation(f));
        }
    }

    public List<Recipient> findAllRecipients() {
        List<Recipient> result = new ArrayList<>();
        conversations.forEach(conversation -> {
            result.add(new Recipient(conversation.getId(), conversation.getRecipient(), conversation.getType(), conversation.getMessages().size()));
        });
        return result;
    }
}
