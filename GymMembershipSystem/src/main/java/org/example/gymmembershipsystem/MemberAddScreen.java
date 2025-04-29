package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        dobField.setPromptText("Date of Birth");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField membershipCreationField = new TextField();
        membershipCreationField.setPromptText("Membership Creation Date");
        ComboBox<String> membershipCombo = new ComboBox<>();
        membershipCombo.getItems().addAll("Basic", "Premium");
        TextField renewedMembership = new TextField();
        renewedMembership.setPromptText("Is membership renewed (True or False)");

        TextField nextPaymentDateField = new TextField();
        nextPaymentDateField.setPromptText("Next Payment Date");
        TextField contractEndDateField = new TextField();
        contractEndDateField.setPromptText("Contract end date");

        TextField latestEntryField = new TextField();
        latestEntryField.setPromptText("Latest Entry");
        TextField balanceField = new TextField();
        balanceField.setPromptText("Balance");

        Button addButton = new Button("Add Member");
        addButton.setOnAction(e -> {
            String firstName = fNameField.getText();
            String lastName = lNameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String dob = dobField.getText();
            String phoneNo = phoneField.getText();
            String address = addressField.getText();
            String creationDate = membershipCreationField.getText();
            Membership membershipType = membershipCombo.getValue().equals("Premium") ? new PremiumMembership() : new RegularMembership();
            boolean renewed = renewedMembership.getText().equals("True");
            String paymentDate = nextPaymentDateField.getText();
            String contractEnd = contractEndDateField.getText();
            String latestEntry = latestEntryField.getText();
            double balance = Double.parseDouble(balanceField.getText());

            Member newMember = new Member(username, password, firstName, lastName,
                    dob, phoneNo, address, creationDate, membershipType,
                    renewed, paymentDate, contractEnd, latestEntry, balance);

            DatabaseManager.insertMember(newMember);
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameField, passwordField,fNameField, lNameField,
                dobField, phoneField, addressField, membershipCreationField, membershipCombo,
                renewedMembership, nextPaymentDateField, contractEndDateField, latestEntryField,
                balanceField, addButton);

        Scene scene = new Scene(layout, 400, 525);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
