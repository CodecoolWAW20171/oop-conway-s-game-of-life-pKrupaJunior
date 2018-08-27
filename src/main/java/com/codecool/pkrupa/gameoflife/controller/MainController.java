package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.UniverseFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {

    public static final String FXML = "/fxml/game_controls.fxml";
    private static final double INITIAL_SPEED = 1000;

    private Stage primaryStage;

    private UniverseController universeController;

    private Timeline timeline;
    private AppState appState;

    @FXML
    private Label generationLabel;

    @FXML
    private Label populationLabel;

    @FXML
    private Slider speedSlider;

    private DoubleProperty speed;

    @FXML
    private Pane universeContainer;

    @FXML
    private Button startBtn;


    @FXML
    void initialize() {
        appState = AppState.SETUP;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUp(Universe universe) {
        this.appState = AppState.SETUP;
        // Create universe display through controller
        universeController = new UniverseController(universe);
        setUpBinds(universe.getGenerationProperty(), universe.getPopulationProperty());
        setUpLoop();
        addUniverseDisplay();
    }

    public void setUp() {
        setUp(new UniverseFactory().getUniverse());
    }

    private void setUpBinds(LongProperty generationProperty, LongProperty populationProperty) {
        // Labels
        generationLabel.textProperty().bind(generationProperty.asString());
        populationLabel.textProperty().bind(populationProperty.asString());

        // Speed slider
        speed = new SimpleDoubleProperty(INITIAL_SPEED);
        speed.bind(speedSlider.valueProperty());
        speedSlider.valueProperty().addListener(observable -> {
            pauseLife();
            setUpLoop();
        });
    }

    private void addUniverseDisplay() {
        universeContainer.getChildren().clear();
        universeContainer.getChildren().add(universeController.getDisplay());
        primaryStage.sizeToScene();
    }

    private double getSpeed() {
        return speed.get();
    }

    private void setUpLoop() {
        Duration duration = new Duration(getSpeed());
        KeyFrame keyFrame = new KeyFrame(duration, event -> universeController.runStep());
        this.timeline = new Timeline(keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    @FXML
    void handleStartBtn() {
        if (appState != AppState.LIFE) {
            startLife();
        } else {
            pauseLife();
        }
    }

    private void startLife() {
        if (appState == AppState.SETUP) {
            universeController.endSetUp();
        }
        appState = AppState.LIFE;
        timeline.play();
        startBtn.setText("Pause");
    }

    private void pauseLife() {
        appState = AppState.PAUSE;
        timeline.stop();
        startBtn.setText("Start");
    }

    // TODO: Pattern loader
    private void loadPattern(String patternName) {
//        PatternLoader patternLoader = new PatternLoader();
//        patternLoader.getClosestCell(10, 20);
    }

    @FXML
    void showUniverseCreator() {
        pauseLife();
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(UniverseCreationController.FXML));
            Pane popUpContent = loader.load();
            UniverseCreationController creationController = loader.getController();
            creationController.setMainController(this);
            creationController.show(primaryStage, popUpContent);
        } catch (IOException e) {
            setUp();
        }
    }

    @FXML
    void resetLife() {
        pauseLife();
        setUp(universeController.getUniverse());
    }

    @FXML
    void exitApp() {
        Platform.exit();
    }


}
