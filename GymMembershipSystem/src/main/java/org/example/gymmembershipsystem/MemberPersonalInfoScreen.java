package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ResourceBundle;

public class MemberPersonalInfoScreen {
    public void showMemberInfoScreen(Member member, ResourceBundle bundle){
        VBox infoBox = new VBox(10);
        Label titleLabel = new Label(bundle.getString("personalInfo"));

        Label nameLabel = new Label(bundle.getString("firstName") + ": " + member.getfName() + " " + member.getlName());
        Label usernameLabel = new Label(bundle.getString("username") + ": " + member.getLoginUsername());
        Label passwordLabel = new Label(bundle.getString("password")+ ": " + member.getLoginPassword());
        Label dobLabel = new Label(bundle.getString("dob")+ ": " + member.getDob());
        Label phoneLabel = new Label(bundle.getString("phoneNo")+ ": " + member.getPhoneNo());
        Label addresslabel = new Label(bundle.getString("address")+ ": " + member.getAddress());

        Button updateButton = new Button(bundle.getString("updateInfo"));
        updateButton.setOnAction(e -> showInfoUpdateScreen(member, bundle));

        infoBox.getChildren().addAll(titleLabel, nameLabel, usernameLabel, passwordLabel,
                dobLabel, phoneLabel, addresslabel, updateButton);

        Stage stage = new Stage();
        Scene scene = new Scene(infoBox, 300, 300);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("profile"));
        stage.show();
    }

    public void showInfoUpdateScreen(Member member, ResourceBundle bundle){
        VBox vBox = new VBox(10);
        Label titleLabel = new Label(bundle.getString("updatePersonalInfo"));

        TextField usernameField = new TextField(member.getLoginUsername());
        usernameField.setPromptText(bundle.getString("username"));
        TextField passwordField = new TextField(member.getLoginPassword());
        passwordField.setPromptText(bundle.getString("password"));
        TextField phoneLabel = new TextField(member.getPhoneNo());
        phoneLabel.setPromptText(bundle.getString("phoneNo"));
        TextField addressLabel = new TextField(member.getAddress());
        addressLabel.setPromptText(bundle.getString("address"));

        Button updateButton = new Button(bundle.getString("update"));

        updateButton.setOnAction(e -> {
            member.setLoginUsername(usernameField.getText());
            member.setLoginPassword(passwordField.getText());
            member.setPhoneNo(phoneLabel.getText());
            member.setAddress(addressLabel.getText());

            DatabaseManager.updateMember(member);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("updatedInfo"));
            alert.showAndWait();
        });

        vBox.getChildren().addAll(titleLabel, usernameField, passwordField, phoneLabel, addressLabel, updateButton);
        Stage stage = new Stage();
        Scene scene = new Scene(vBox, 400, 350);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("updatePersonalInfo"));
        stage.show();
    }
}
