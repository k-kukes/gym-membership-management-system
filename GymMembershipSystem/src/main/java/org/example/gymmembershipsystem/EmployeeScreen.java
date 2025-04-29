package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;


public class EmployeeScreen extends Application {
    private Employee employee;

    public EmployeeScreen(Employee employee){
        this.employee = employee;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Employee Main Screen");
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label("Welcome Employee " + employee.getfName() + " " + employee.getlName());

        Button manageMembersButton = new Button("Manage Members");
        manageMembersButton.setOnAction(e -> manageMembers());

        Button viewLogsButton = new Button("View Activity Logs");
        viewLogsButton.setOnAction(e -> viewActivityLogs());

        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.setOnAction(e -> {
            try {
                EmployeeAddScreen.show();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen();
            Stage loginStage = new Stage();
            try {
                loginScreen.start(loginStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        layout.getChildren().addAll(welcomeLabel, manageMembersButton, viewLogsButton, addEmployeeButton,logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void manageMembers(){
        System.out.println("Managing members...");
        MemberManagerScreen memberManagerScreen = new MemberManagerScreen(employee);
        Stage stage = new Stage();
        try {
            memberManagerScreen.start(stage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void viewActivityLogs(){
        System.out.println("Viewing employee activity logs...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
