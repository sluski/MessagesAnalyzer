package pl.slupski.analyzer;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AnalyzerService {

    private List<Message> messages;

    public void init(File file) throws IOException {
        this.messages = DataExtractor.extractMessages(file);
        int i = 2;
    }

    public List<Message> findAll() {
        return messages;
    }

}
