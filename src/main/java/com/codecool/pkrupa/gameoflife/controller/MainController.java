package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.simple.SimpleUniverse;
import com.codecool.pkrupa.gameoflife.view.UniverseDisplay;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
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
    private static final int DEFAULT_UNIVERSE_WIDTH = 80;
    private static final int DEFAULT_UNIVERSE_HEIGHT = 50;
    private static final boolean DEFAULT_WRAPPING = false;
    private static final double INITIAL_SPEED = 1000;

    private Stage primaryStage;
    private Universe universe;
    private UniverseDisplay universeDisplay;
    private Timeline timeline;
    private DoubleProperty speed;
    private AppState appState;

    @FXML
    private Label generation;

    @FXML
    private Slider speedSlider;

    @FXML
    private Pane universeContainer;

    @FXML
    private Button startBtn;

    @FXML
    private Label population;

    @FXML
    void initialize() {
        this.appState = AppState.SETUP;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUp() {
        setUp(new SimpleUniverse(DEFAULT_UNIVERSE_HEIGHT, DEFAULT_UNIVERSE_WIDTH, DEFAULT_WRAPPING));
    }

    public void setUp(Universe universe) {
        this.appState = AppState.SETUP;
        setUpUniverse(universe);
        setUpBinds();
        setUpLoop();
        this.primaryStage.sizeToScene();
    }

    private void setUpBinds() {
        this.generation.textProperty().bind(this.universe.getGenerationProperty().asString());
        this.population.textProperty().bind(this.universe.getPopulationProperty().asString());
        this.speed = new SimpleDoubleProperty(INITIAL_SPEED);
        this.speed.bind(this.speedSlider.valueProperty());
        this.speedSlider.valueProperty().addListener(observable -> {
            pauseLife();
            setUpLoop();
        });
    }

    private double getSpeed() {
        return speed.get();
    }

    private void setUpUniverse(Universe universe) {
        this.universe = universe;
        this.universeDisplay = new UniverseDisplay(this.universe);

        this.universeContainer.getChildren().clear();
        this.universeContainer.getChildren().add(this.universeDisplay);
        this.universeDisplay.setUpGrid();
    }

    private void setUpLoop() {
        Duration duration = new Duration(getSpeed());
        KeyFrame keyFrame = new KeyFrame(duration, event -> runStepWithDisplay());
        this.timeline = new Timeline(keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void runStepWithDisplay() {
        this.universe.runStep();
        this.universeDisplay.updateDisplay();
    }

    @FXML
    void handleStartBtn() {
        if (this.appState != AppState.LIFE) {
            startLife();
        } else {
            pauseLife();
        }
    }

    private void startLife() {
        if (this.appState == AppState.SETUP) {
            this.universeDisplay.endSetUp();
        }
        this.appState = AppState.LIFE;
        this.timeline.play();
        this.startBtn.setText("Pause");
    }

    private void pauseLife() {
        this.appState = AppState.PAUSE;
        this.timeline.stop();
        this.startBtn.setText("Start");
    }

    private void loadPattern(String patternName) {
        PatternLoader patternLoader = new PatternLoader(universe);
        patternLoader.getClosestCell(10, 20);
    }

    @FXML
    void requestNewUniverse() {
        pauseLife();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(UniverseCreationController.FXML));
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
        setUp();
    }

    @FXML
    void exitApp() {
        Platform.exit();
    }

    Stage getPrimaryStage() {
        return primaryStage;
    }
}
