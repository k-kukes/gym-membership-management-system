package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class EmployeeScreen{
    private Employee employee;

    public EmployeeScreen(Employee employee){
        this.employee = employee;
    }

    public void start(Stage stage, ResourceBundle bundle) {
        stage.setTitle(bundle.getString("employeeScreen"));
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label(bundle.getString("welcomeEmployee") + " " + employee.getfName() + " " + employee.getlName());

        Button manageMembersButton = new Button(bundle.getString("manageMembers"));
        manageMembersButton.setOnAction(e -> manageMembers(bundle));

        Button manageEmployeesButton = new Button(bundle.getString("manageEmployees"));
        manageEmployeesButton.setOnAction(e -> manageEmployees(bundle));

        Button viewLogsButton = new Button(bundle.getString("viewLogs"));
        viewLogsButton.setOnAction(e -> viewActivityLogs(bundle));

        Button logoutButton = new Button(bundle.getString("logout"));
        logoutButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen();
            Stage loginStage = new Stage();
            try {
                loginScreen.start(loginStage, bundle);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        layout.getChildren().addAll(welcomeLabel, manageMembersButton, manageEmployeesButton,viewLogsButton,logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void manageMembers(ResourceBundle bundle){
        MemberManagerScreen memberManagerScreen = new MemberManagerScreen(employee);
        Stage stage = new Stage();
        try {
            memberManagerScreen.start(stage, bundle);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void manageEmployees(ResourceBundle bundle){
        EmployeeManagerScreen employeeManagerScreen = new EmployeeManagerScreen(employee);
        Stage stage = new Stage();
        try {
            employeeManagerScreen.start(stage, bundle);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void viewActivityLogs(ResourceBundle bundle){
        LogScreen screen = new LogScreen();
        screen.showLogScreen(employee, bundle);
    }

}
