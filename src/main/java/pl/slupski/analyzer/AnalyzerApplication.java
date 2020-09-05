package pl.slupski.analyzer;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyzerApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AnalyzerApplication.class, args);
		Application.launch(JavaFxApplication.class, args);
    }

}
