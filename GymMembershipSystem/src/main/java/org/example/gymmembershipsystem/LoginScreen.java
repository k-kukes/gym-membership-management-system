package org.example.gymmembershipsystem;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreen {
    private TextField usernameField;
    private PasswordField passwordField;

    public void start(Stage stage, ResourceBundle bundle){
        Label usernameLabel = new Label(bundle.getString("username") + ":");
        usernameField = new TextField();
        Label passwordLabel = new Label(bundle.getString("password") + ":");
        passwordField = new PasswordField();

        Label chooseLoginLabel = new Label(bundle.getString("loginPick") + ":");
        Button loginMemberButton = new Button(bundle.getString("member") + ":");
        Button loginEmployeeButton = new Button(bundle.getString("employee") + ":");

        loginMemberButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Member member = MemberManager.login(username, password);
            if (member != null) {
                MemberScreen memberScreen = new MemberScreen(member);
                Stage memberStage = new Stage();
                try {
                    memberScreen.start(memberStage, bundle);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, bundle.getString("incorrectLogin"));
                alert.showAndWait();
            }
        });

        loginEmployeeButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Employee employee = EmployeeManager.login(username, password);
            if (employee != null){
                System.out.println("Employee logged in successfully!");
                EmployeeScreen employeeScreen = new EmployeeScreen(employee);
                Stage employeeStage = new Stage();
                try {
                    employeeScreen.start(employeeStage, bundle);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, bundle.getString("incorrectLogin"));
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, chooseLoginLabel,
                loginMemberButton, loginEmployeeButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("loginTitle"));
        stage.show();
    }

}
