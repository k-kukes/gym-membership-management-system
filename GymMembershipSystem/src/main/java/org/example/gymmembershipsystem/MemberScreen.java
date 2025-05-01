package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MemberScreen extends Application {
    private Member member;

    public MemberScreen(Member member){
        this.member = member;
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Member Screen");
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label("Welcome " + member.getfName() + " " + member.getlName() + "!");

        Button viewDetailsButton = new Button("View Membership Details");
        viewDetailsButton.setOnAction(e -> viewMembershipDetails());

        Button updateContactButton = new Button("Update Personal Info");
        updateContactButton.setOnAction(e -> updateContactInfo());


        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            stage.close();
            LoginScreen loginScreen = new LoginScreen();
            Stage loginStage = new Stage();
            try {
                loginScreen.start(loginStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        layout.getChildren().addAll(welcomeLabel, viewDetailsButton, updateContactButton, logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void viewMembershipDetails(){
        MembershipManagerScreen managerScreen = new MembershipManagerScreen();
        managerScreen.show(member);
    }

    private void updateContactInfo(){
        MemberPersonalInfoScreen memberPersonalInfoScreen = new MemberPersonalInfoScreen();
        memberPersonalInfoScreen.showMemberInfoScreen(member);
    }
}
