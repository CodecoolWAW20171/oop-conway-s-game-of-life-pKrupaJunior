package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.UniverseType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UniverseCreationController {

    static final String FXML = "/fxml/universe_creation.fxml";

    private Stage popUp;
    private UniverseController universeController;
    private MainController mainController;

    @FXML
    private TextField rowsInput;

    @FXML
    private TextField columnsInput;

    @FXML
    private CheckBox wrappingInput;

    @FXML
    private ChoiceBox<UniverseType> universeTypeChoice;


    @FXML
    private void initialize() {
        universeTypeChoice.getItems().addAll(UniverseType.values());
        universeTypeChoice.getSelectionModel().selectFirst();
        wrappingInput.setSelected(false);
    }

    void show(Stage stageOwner, Pane root) {
        popUp = new Stage(StageStyle.UTILITY);
        popUp.initOwner(stageOwner);
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("Create new universe");
        popUp.setScene(new Scene(root));
        popUp.showAndWait();
    }

    @FXML
    void createUniverse() {
        try {
            int rows = Integer.parseInt(rowsInput.getText());
            int columns = Integer.parseInt(columnsInput.getText());
            boolean wrapping = wrappingInput.isSelected();
            UniverseType type = universeTypeChoice.getValue();
            Universe universe = universeController.createNewUniverse(type, rows, columns, wrapping);
            mainController.setUp(universe);
            popUp.close();
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    void cancelCreation() {
        popUp.close();
    }

    void setUniverseController(UniverseController universeController) {
        this.universeController = universeController;
    }

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
