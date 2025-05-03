package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class MemberScreen {
    private Member member;

    public MemberScreen(Member member){
        this.member = member;
    }

    public void start(Stage stage, ResourceBundle bundle) {
        stage.setTitle(bundle.getString("memberScreen"));
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label(bundle.getString("welcome") + " " + member.getfName() + " " + member.getlName() + "!");

        Button viewDetailsButton = new Button(bundle.getString("viewMemDetails"));
        viewDetailsButton.setOnAction(e -> viewMembershipDetails(bundle));

        Button updateContactButton = new Button(bundle.getString("updatePersonalInfo"));
        updateContactButton.setOnAction(e -> updateContactInfo(bundle));


        Button logoutButton = new Button(bundle.getString("logout"));
        logoutButton.setOnAction(e -> {
            stage.close();
            LoginScreen loginScreen = new LoginScreen();
            Stage loginStage = new Stage();
            try {
                loginScreen.start(loginStage, bundle);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        layout.getChildren().addAll(welcomeLabel, viewDetailsButton, updateContactButton, logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void viewMembershipDetails(ResourceBundle bundle){
        MembershipManagerScreen managerScreen = new MembershipManagerScreen();
        managerScreen.show(member, bundle);
    }

    private void updateContactInfo(ResourceBundle bundle){
        MemberPersonalInfoScreen memberPersonalInfoScreen = new MemberPersonalInfoScreen();
        memberPersonalInfoScreen.showMemberInfoScreen(member, bundle);
    }
}
