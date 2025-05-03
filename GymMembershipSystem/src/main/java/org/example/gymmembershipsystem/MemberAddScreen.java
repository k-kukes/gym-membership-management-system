package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MemberAddScreen {
    public void showStage(Stage stage, Employee employee, ResourceBundle bundle) throws Exception {
        stage.setTitle(bundle.getString("addMember"));

        TextField usernameField = new TextField();
        usernameField.setPromptText(bundle.getString("username"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(bundle.getString("password"));
        TextField fNameField = new TextField();
        fNameField.setPromptText(bundle.getString("firstName"));
        TextField lNameField = new TextField();
        lNameField.setPromptText(bundle.getString("lastName"));

        TextField dobField = new TextField();
        dobField.setPromptText(bundle.getString("dobFormat"));
        TextField phoneField = new TextField();
        phoneField.setPromptText(bundle.getString("phoneFormat"));
        TextField addressField = new TextField();
        addressField.setPromptText(bundle.getString("address"));

        TextField membershipCreationField = new TextField();
        membershipCreationField.setPromptText(bundle.getString("memCreationDate") + ":");
        Label membershipTypeLabel = new Label(bundle.getString("memType") + ":");
        ComboBox<String> membershipCombo = new ComboBox<>();
        membershipCombo.getItems().addAll(bundle.getString("regular"), bundle.getString("premium"));
        Label renewedMembershipLabel = new Label(bundle.getString("isMemRenewed") + ":");
        ComboBox<Boolean> renewedMembershipBox = new ComboBox<>();
        renewedMembershipBox.getItems().addAll(Boolean.TRUE, Boolean.FALSE);

        TextField nextPaymentDateField = new TextField();
        nextPaymentDateField.setPromptText(bundle.getString("nextPaymentFormat"));
        TextField contractEndDateField = new TextField();
        contractEndDateField.setPromptText(bundle.getString("contractEndFormat"));

        TextField latestEntryField = new TextField();
        latestEntryField.setPromptText(bundle.getString("latestEntry"));
        TextField balanceField = new TextField();
        balanceField.setPromptText(bundle.getString("balancePrompt"));

        Button addButton = new Button(bundle.getString("addMember"));
        addButton.setOnAction(e -> {
            // fname, lname, username, password should be NOT NULL
            // membershipType, renewedMembership, nextPayment, contractEnd, balance should be NOT NULL
            if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
            || membershipCombo.getValue() == null || renewedMembershipBox.getValue() == null || nextPaymentDateField.getText().isEmpty()
            || contractEndDateField.getText().isEmpty() || balanceField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("memCreationFields"));
                alert.showAndWait();
            }
            else {
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String dob = dobField.getText();
                String phoneNo = phoneField.getText();
                String address = addressField.getText();
                String creationDate = membershipCreationField.getText();
                Membership membershipType = membershipCombo.getValue().equals("Premium") ? MembershipFactory.craete("premium"): MembershipFactory.craete("regular");
                boolean renewed = renewedMembershipBox.getValue();
                String paymentDate = nextPaymentDateField.getText();
                String contractEnd = contractEndDateField.getText();
                String latestEntry = latestEntryField.getText();
                double balance = Double.parseDouble(balanceField.getText());

                Member newMember = new Member(username, password, firstName, lastName,
                        dob, phoneNo, address, creationDate, membershipType,
                        renewed, paymentDate, contractEnd, latestEntry, balance);

                DatabaseManager.insertMember(newMember);
                employee.setLatestLog(employee.getLatestLog() +
                        "\n" + "Added Member " + newMember.getfName() + " " + newMember.getlName());
                DatabaseManager.updateLogs(employee);

                Alert addedMemberAlert = new Alert(Alert.AlertType.INFORMATION,bundle.getString("memberSuccess"));
                addedMemberAlert.showAndWait();
            }
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameField, passwordField,fNameField, lNameField,
                dobField, phoneField, addressField, membershipCreationField, membershipTypeLabel,membershipCombo,
                renewedMembershipLabel,renewedMembershipBox, nextPaymentDateField, contractEndDateField, latestEntryField,
                balanceField, addButton);

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}
