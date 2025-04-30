package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberAddScreen extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Add New Member");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField fNameField = new TextField();
        fNameField.setPromptText("First Name");
        TextField lNameField = new TextField();
        lNameField.setPromptText("Last Name");

        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth (Format: YYYY-MM-DD)");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number (Format: XXX-XXX-XXXX)");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField membershipCreationField = new TextField();
        membershipCreationField.setPromptText("Membership Creation Date");
        Label membershipTypeLabel = new Label("Membership Type:");
        ComboBox<String> membershipCombo = new ComboBox<>();
        membershipCombo.getItems().addAll("Basic", "Premium");
        Label renewedMembershipLabel = new Label("Is Membership Renewed:");
        ComboBox<Boolean> renewedMembershipBox = new ComboBox<>();
        renewedMembershipBox.getItems().addAll(Boolean.TRUE, Boolean.FALSE);

        TextField nextPaymentDateField = new TextField();
        nextPaymentDateField.setPromptText("Next Payment Date (Format: YYYY-MM-DD)");
        TextField contractEndDateField = new TextField();
        contractEndDateField.setPromptText("Contract end date (Format: YYYY-MM-DD)");

        TextField latestEntryField = new TextField();
        latestEntryField.setPromptText("Latest Entry (Format: YYYY-MM-DD)");
        TextField balanceField = new TextField();
        balanceField.setPromptText("Balance (Just Number)");

        Button addButton = new Button("Add Member");
        addButton.setOnAction(e -> {
            // fname, lname, username, password should be NOT NULL
            // membershipType, renewedMembership, nextPayment, contractEnd, balance should be NOT NULL
            if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
            || membershipCombo.getValue() == null || renewedMembershipBox.getValue() == null || nextPaymentDateField.getText().isEmpty()
            || contractEndDateField.getText().isEmpty() || balanceField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Following Fields should contain a value: \n" +
                        "First Name, Last Name, username, password, membership Type, Membership renewed, Next Payment Date" +
                        ", Contract End Date and Balance");
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
                Membership membershipType = membershipCombo.getValue().equals("Premium") ? new PremiumMembership() : new RegularMembership();
                boolean renewed = renewedMembershipBox.getValue();
                String paymentDate = nextPaymentDateField.getText();
                String contractEnd = contractEndDateField.getText();
                String latestEntry = latestEntryField.getText();
                double balance = Double.parseDouble(balanceField.getText());

                Member newMember = new Member(username, password, firstName, lastName,
                        dob, phoneNo, address, creationDate, membershipType,
                        renewed, paymentDate, contractEnd, latestEntry, balance);

                DatabaseManager.insertMember(newMember);
                Alert addedMemberAlert = new Alert(Alert.AlertType.INFORMATION, "Member was successfully added!");
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

    public static void main(String[] args) {
        launch(args);
    }
}
