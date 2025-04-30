package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberUpdateScreen {
    public static void showUpdateForm(Member member){
        Stage stage = new Stage();
        stage.setTitle("Update Member");

        Label fnameLabel = new Label("First Name:");
        TextField fNameField = new TextField(member.getfName());
        fNameField.setPromptText("First Name");
        Label lnameLabel = new Label("Last Name:");
        TextField lNameField = new TextField(member.getlName());
        lNameField.setPromptText("Last Name");
        Label phoneNoLabel = new Label("Phone Number:");
        TextField phoneNoField = new TextField(member.getPhoneNo());
        phoneNoField.setPromptText("Phone No");
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField(member.getAddress());
        addressField.setPromptText("Address");
        Label membershipTypeLabel = new Label("Membership Type:");
        ComboBox<String> membershipComboBox = new ComboBox<>();
        membershipComboBox.getItems().setAll("Premium", "Regular");
        membershipComboBox.setValue(member.getMembershipType().getType());

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            member.setfName(fNameField.getText());
            member.setlName(lNameField.getText());
            member.setPhoneNo(phoneNoField.getText());
            member.setAddress(addressField.getText());
            if (membershipComboBox.getValue().equals("Premium"))
                member.setMembershipType(new PremiumMembership());
            else
                member.setMembershipType(new RegularMembership());
            DatabaseManager.updateMember(member);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setContentText("Member updated successfully!");
            success.showAndWait();
            stage.close();
        });

        VBox layout = new VBox(10, fnameLabel,fNameField, lnameLabel,lNameField,
                phoneNoLabel,phoneNoField, addressLabel,addressField, membershipTypeLabel,membershipComboBox, saveButton);
        Scene scene = new Scene(layout, 300, 350);
        stage.setScene(scene);
        stage.show();
    }
}
