package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeAddScreen{

    public static void show() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Add New Employee");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth");
        TextField phoneNoField = new TextField();
        phoneNoField.setPromptText("Phone Number");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        TextField dateHiredField = new TextField();
        dateHiredField.setPromptText("Date Hired");
        TextField latestLogField = new TextField();
        latestLogField.setPromptText("Latest log");

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
            || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()){
                Alert warningAlert = new Alert(Alert.AlertType.ERROR, "Following Fields should contain a value: \n" +
                        "First Name, Last Name, username and password");
                warningAlert.showAndWait();
            }
            else {
                Employee newEmployee = new Employee(usernameField.getText(), passwordField.getText(),
                        firstNameField.getText(), lastNameField.getText(), dobField.getText(),
                        phoneNoField.getText(), addressField.getText(), dateHiredField.getText(),
                        latestLogField.getText());

                DatabaseManager.insertEmployee(newEmployee);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee added successfully");
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
