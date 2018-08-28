package com.codecool.pkrupa.gameoflife;

import com.codecool.pkrupa.gameoflife.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String APP_NAME = "Game of Life";
    private static final String ICON = "/img/glider.png";

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MainController.FXML));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.init();

        primaryStage.setTitle(APP_NAME);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON)));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
