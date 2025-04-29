package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberUpdateScreen {
    public static void showUpdateForm(Member member){
        Stage stage = new Stage();
        stage.setTitle("Update Member");

        TextField fNameField = new TextField(member.getfName());
        TextField lNameField = new TextField(member.getlName());
        TextField phoneNoField = new TextField(member.getPhoneNo());
        TextField addressField = new TextField(member.getAddress());
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

        VBox layout = new VBox(10, fNameField, lNameField, phoneNoField, addressField, membershipComboBox, saveButton);
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
}
