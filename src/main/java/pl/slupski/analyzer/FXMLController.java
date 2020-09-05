package pl.slupski.analyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
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

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    protected void handleSelectButton(ActionEvent event) {
        File selectedDirectory = directoryChooser.showDialog(vBoxPane.getScene().getWindow());
        pathInput.setText(selectedDirectory.getAbsolutePath());
        log("Selected folder with path: " + selectedDirectory.getAbsolutePath());
        findHtmlFiles(selectedDirectory);
    }

    @FXML
    protected void handleAnalyzeButton(ActionEvent event) {
        log("Analyzing file: " + listView.getSelectionModel().getSelectedItem().getName());
        try {
            analyzerService.init(listView.getSelectionModel().getSelectedItem());
            log("Analyzing finished successfully");
        } catch (IOException ex) {
            log("Error while analyzing: " + ex.getCause());
        }
    }

    private void findHtmlFiles(File directory) {
        FilenameFilter htmlFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".html")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] files = directory.listFiles(htmlFilter);
        ObservableList items = FXCollections.observableArrayList();
        Arrays.asList(files).forEach(file -> {
            items.add(file);
            log("Found file: " + file);
        });
        listView.setItems(items);
    }

    private void log(String log) {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        logsArea.appendText("(" + format.format(new Date()) + ") " + log + "\n");
    }
}
