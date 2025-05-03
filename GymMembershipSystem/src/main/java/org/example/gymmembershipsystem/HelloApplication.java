package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox vBox = new VBox(10);
        Button frenchButton = new Button("Francais");
        Button englishButton = new Button("English");
        vBox.getChildren().addAll(frenchButton, englishButton);

        LoginScreen loginScreen = new LoginScreen();
        Locale frenchLocale = new Locale("fr", "FR");
        Locale englishLocale = new Locale("en", "US");
        englishButton.setOnAction(e -> loginScreen.start(stage, ResourceBundle.getBundle("Messages", englishLocale)));
        frenchButton.setOnAction(e -> loginScreen.start(stage, ResourceBundle.getBundle("Messages", frenchLocale)));

        Scene scene = new Scene(vBox, 300, 100);
        stage.setTitle("Choose Language");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}