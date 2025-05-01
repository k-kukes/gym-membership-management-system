package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MembershipManagerScreen {
    public void show(Member member){
        Label membershipTypeLabel = new Label("Membership Type: " + member.getMembershipType().getType());
        Label nextPaymentDateLabel = new Label("Next Payment Date: " + member.getNextPaymentDate());
        Label contractEndDateLabel = new Label("Contract End Date: " + member.getContractEndDate());
        Label renewedLabel = new Label("Renewed: " + member.isRenewedMembership());
        Button renewButton = new Button("Renew Membership");
        Button switchButton = new Button("Switch Membership");
        Button cancelButton = new Button("Cancel Membership");

        VBox vBox = new VBox(10, membershipTypeLabel, nextPaymentDateLabel, contractEndDateLabel,
                renewedLabel, renewButton, switchButton, cancelButton);

        Scene scene = new Scene(vBox, 300, 300);
        Stage stage = new Stage();
        stage.setTitle("Membership Management");
        stage.setScene(scene);
        stage.show();
    }
}
