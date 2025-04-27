package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class DashboardScreen extends Application {
    private String role;

    public DashboardScreen(String role){
        this.role = role;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(role + "Dashboard");
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label("Welcome to the " + role + " Dashboard!");
        Button actionButtons = createActionButton();

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            stage.close();
            try {
                openLoginScreen();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());;
            }
        });

        layout.getChildren().addAll(welcomeLabel, actionButtons, logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private Button createActionButton(){
        Button actionButton = new Button();

        if (role.equals("Member")) {
            actionButton.setText("View Membership Details");
            actionButton.setOnAction(e ->  viewMembershipDetails());
        } else if (role.equals("Employee")) {
            actionButton.setText("Manage Members");
            actionButton.setOnAction(e -> manageMembers());
        }

        return actionButton;
    }

    private void viewMembershipDetails(){
        System.out.println("Viewing membership details...");
    }

    private void manageMembers(){
        System.out.println("Managing members...");
    }

    private void openLoginScreen() throws Exception {
        LoginScreen loginScreen = new LoginScreen();
        Stage loginStage = new Stage();
        loginScreen.start(loginStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

