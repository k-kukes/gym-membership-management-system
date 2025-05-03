package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MemberUpdateScreen {
    public static void showUpdateForm(Member member, Employee employee, ResourceBundle bundle){
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("updateMember"));

        Label fnameLabel = new Label(bundle.getString("username")  + ":");
        TextField fNameField = new TextField(member.getfName());
        fNameField.setPromptText(bundle.getString("firstName")  + ":");
        Label lnameLabel = new Label("Last Name:");
        TextField lNameField = new TextField(member.getlName());
        lNameField.setPromptText(bundle.getString("lastName")  + ":");
        Label phoneNoLabel = new Label(bundle.getString("phoneNo"));
        TextField phoneNoField = new TextField(member.getPhoneNo());
        phoneNoField.setPromptText(bundle.getString("phoneNo"));
        Label addressLabel = new Label(bundle.getString("address"));
        TextField addressField = new TextField(member.getAddress());
        addressField.setPromptText(bundle.getString("address"));
        Label membershipTypeLabel = new Label(bundle.getString("memType"));
        ComboBox<String> membershipComboBox = new ComboBox<>();
        membershipComboBox.getItems().setAll(bundle.getString("premium"), bundle.getString("regular"));
        membershipComboBox.setValue(member.getMembershipType().getType());

        Button saveButton = new Button(bundle.getString("save"));
        saveButton.setOnAction(e -> {
            member.setfName(fNameField.getText());
            member.setlName(lNameField.getText());
            member.setPhoneNo(phoneNoField.getText());
            member.setAddress(addressField.getText());
            if (membershipComboBox.getValue().equals("Premium"))
                member.setMembershipType(MembershipFactory.craete("premium"));
            else
                member.setMembershipType(MembershipFactory.craete("regular"));
            DatabaseManager.updateMember(member);

            employee.setLatestLog(employee.getLatestLog() + "\n" + "Updated Member " + member.getfName() + " " + member.getlName());
            DatabaseManager.updateLogs(employee);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle(bundle.getString("success"));
            success.setContentText(bundle.getString("memberUpdateSuccess"));
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
