package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class LoginScreen extends Application {
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void start(Stage stage) throws Exception {
        Label usernameLabel = new Label("Username: ");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password: ");
        passwordField = new PasswordField();

        Label chooseLoginLabel = new Label("Login As: ");
        Button loginMemberButton = new Button("Member");
        Button loginEmployeeButton = new Button("Employee");

        loginMemberButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Member member = MemberManager.login(username, password);
            if (member != null) {
                System.out.println("Member logged in successfully!");
                MemberScreen memberScreen = new MemberScreen(member);
                Stage memberStage = new Stage();
                try {
                    memberScreen.start(memberStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();
            }
            else {
                System.out.println("Invalid username or password");
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
                    employeeScreen.start(employeeStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();
            }
            else {
                System.out.println("Invalid username or password");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, chooseLoginLabel, loginMemberButton, loginEmployeeButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.setTitle("Gym Management System LOGIN");
        stage.show();
    }

    private void openMainScreen(String role) throws Exception {
        DashboardScreen dashboardScreen = new DashboardScreen(role);
        Stage dashboardStage = new Stage();
        dashboardScreen.start(dashboardStage);

        Stage loginStage = (Stage) usernameField.getScene().getWindow();
        loginStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
