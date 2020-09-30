package pl.slupski.analyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@FxmlView("main-scene.fxml")
public class FXMLController {

    @Autowired
    private AnalyzerService analyzerService;

    @FXML
    private TextField pathInput;
    @FXML
    private VBox vBoxPane;
    @FXML
    private TextArea logsArea;

    @FXML
    private ListView<File> listView;

    private FileChooser directoryChooser = new FileChooser();
    private File selectedDirectory;

    @FXML
    protected void handleSelectButton(ActionEvent event) {
        selectedDirectory = directoryChooser.showOpenDialog(vBoxPane.getScene().getWindow());
        pathInput.setText(selectedDirectory.getAbsolutePath());
        log("Selected folder with path: " + selectedDirectory.getAbsolutePath());
    }

    @FXML
    protected void handleAnalyzeButton(ActionEvent event) {
        System.out.println("Analyzing...");
        analyzerService.init(selectedDirectory);
        System.out.println("Analyzed!");
        log("Data pulled out");
    }

    private void log(String log) {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        logsArea.appendText("(" + format.format(new Date()) + ") " + log + "\n");
    }
}
