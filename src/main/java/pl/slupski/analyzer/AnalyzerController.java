package pl.slupski.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analyzer")
public class AnalyzerController {

    @Autowired
    private AnalyzerService analyzerService;

    @GetMapping("/about")
    public String about() {
        return "Messages Analyzer v1.0.0";
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return analyzerService.findAll();
    }
}
