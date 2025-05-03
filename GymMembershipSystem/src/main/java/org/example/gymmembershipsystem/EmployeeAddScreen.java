package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeAddScreen{

    public static void show(ResourceBundle bundle) throws Exception {
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("employeeAdd"));

        TextField usernameField = new TextField();
        usernameField.setPromptText(bundle.getString("username"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(bundle.getString("password"));
        TextField firstNameField = new TextField();
        firstNameField.setPromptText(bundle.getString("firstName"));
        TextField lastNameField = new TextField();
        lastNameField.setPromptText(bundle.getString("lastName"));
        TextField dobField = new TextField();
        dobField.setPromptText(bundle.getString("dob"));
        TextField phoneNoField = new TextField();
        phoneNoField.setPromptText(bundle.getString("phoneNo"));
        TextField addressField = new TextField();
        addressField.setPromptText(bundle.getString("address"));
        TextField dateHiredField = new TextField();
        dateHiredField.setPromptText(bundle.getString("dateHired"));
        TextField latestLogField = new TextField();
        latestLogField.setPromptText(bundle.getString("latestLog"));

        Button addButton = new Button(bundle.getString("employeeAdd"));
        addButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
            || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()){
                Alert warningAlert = new Alert(Alert.AlertType.ERROR, bundle.getString("fields"));
                warningAlert.showAndWait();
            }
            else {
                Employee newEmployee = new Employee(usernameField.getText(), passwordField.getText(),
                        firstNameField.getText(), lastNameField.getText(), dobField.getText(),
                        phoneNoField.getText(), addressField.getText(), dateHiredField.getText(),
                        latestLogField.getText());

                DatabaseManager.insertEmployee(newEmployee);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("successEmployee"));
                alert.showAndWait();
            }
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameField, passwordField, firstNameField, lastNameField,
                dobField, phoneNoField, addressField, dateHiredField, latestLogField, addButton);
        Scene scene = new Scene(layout, 400, 525);
        stage.setScene(scene);
        stage.show();
    }
}
