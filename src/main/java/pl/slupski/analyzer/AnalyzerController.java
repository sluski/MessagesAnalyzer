package pl.slupski.analyzer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analyzer")
public class AnalyzerController {

    @GetMapping("/about")
    public String about() {
        return "Messages Analyzer v1.0.0";
    }
}
