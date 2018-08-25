package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.simple.SimpleUniverse;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UniverseCreationController {

    static final String FXML = "/fxml/universe_creation.fxml";

    private MainController mainController;
    private Stage popUp;

    @FXML
    private TextField rowsInput;

    @FXML
    private TextField columnsInput;

    @FXML
    private CheckBox wrappingInput;

    @FXML
    private ChoiceBox universeTypeChoice;


    @FXML
    private void initialize() {
        universeTypeChoice.setItems(FXCollections.observableArrayList(
                SimpleUniverse.getName()
        ));
        universeTypeChoice.getSelectionModel().selectFirst();
    }

    public void show(Stage stageOwner, Pane root) {
        this.popUp = new Stage(StageStyle.UTILITY);
        this.popUp.initOwner(stageOwner);
        this.popUp.initModality(Modality.APPLICATION_MODAL);
        this.popUp.setTitle("Create new universe");
        this.popUp.setScene(new Scene(root));
        this.popUp.showAndWait();
    }

    @FXML
    void createUniverse() {
        try {
            int rows = Integer.parseInt(this.rowsInput.getText());
            int columns = Integer.parseInt(this.columnsInput.getText());
            boolean wrapping = wrappingInput.isSelected();
            if (universeTypeChoice.getValue() == SimpleUniverse.getName()) {
                mainController.setUp(new SimpleUniverse(rows, columns, wrapping));
                mainController.getPrimaryStage().sizeToScene();
                this.popUp.close();
            }
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    void cancelCreation() {
        this.popUp.close();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
