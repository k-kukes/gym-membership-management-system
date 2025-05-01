package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class MemberPersonalInfoScreen {
    public void showMemberInfoScreen(Member member){
        VBox infoBox = new VBox(10);
        Label titleLabel = new Label("Your Personal Information");

        Label nameLabel = new Label("Full Name: " + member.getfName() + " " + member.getlName());
        Label usernameLabel = new Label("Username: " + member.getLoginUsername());
        Label passwordLabel = new Label("Password: " + member.getLoginPassword());
        Label dobLabel = new Label("Date Of Birth: " + member.getDob());
        Label phoneLabel = new Label("Phone Number: " + member.getPhoneNo());
        Label addresslabel = new Label("Address: " + member.getAddress());

        Button updateButton = new Button("Update Info");
        updateButton.setOnAction(e -> showInfoUpdateScreen(member));

        infoBox.getChildren().addAll(titleLabel, nameLabel, usernameLabel, passwordLabel,
                dobLabel, phoneLabel, addresslabel, updateButton);

        Stage stage = new Stage();
        Scene scene = new Scene(infoBox, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Your Profile");
        stage.show();
    }

    public void showInfoUpdateScreen(Member member){
        VBox vBox = new VBox(10);
        Label titleLabel = new Label("Update Personal Information");

        TextField usernameField = new TextField(member.getLoginUsername());
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField(member.getLoginPassword());
        passwordField.setPromptText("Password");
        TextField phoneLabel = new TextField(member.getPhoneNo());
        phoneLabel.setPromptText("Phone Number");
        TextField addressLabel = new TextField(member.getAddress());
        addressLabel.setPromptText("Address");

        Button updateButton = new Button("Update");

        updateButton.setOnAction(e -> {
            member.setLoginUsername(usernameField.getText());
            member.setLoginPassword(passwordField.getText());
            member.setPhoneNo(phoneLabel.getText());
            member.setAddress(addressLabel.getText());

            DatabaseManager.updateMember(member);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Updated your information");
            alert.showAndWait();
        });

        vBox.getChildren().addAll(titleLabel, usernameField, passwordField, phoneLabel, addressLabel, updateButton);
        Stage stage = new Stage();
        Scene scene = new Scene(vBox, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Update Your Personal Information");
        stage.show();
    }
}
