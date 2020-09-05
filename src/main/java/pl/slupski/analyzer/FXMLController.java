package pl.slupski.analyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
@FxmlView("main-scene.fxml")
public class FXMLController {


    @FXML private TextField pathInput;
    @FXML private GridPane gridPane;

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        File selectedDirectory = directoryChooser.showDialog(gridPane.getScene().getWindow());
        pathInput.setText(selectedDirectory.getAbsolutePath());
    }
}
