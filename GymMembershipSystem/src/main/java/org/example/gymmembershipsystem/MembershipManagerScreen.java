package org.example.gymmembershipsystem;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class MembershipManagerScreen {
    public void show(Member member, ResourceBundle bundle){
        Label membershipTypeLabel = new Label(bundle.getString("memType") + ": " + member.getMembershipType().getType());
        Label nextPaymentDateLabel = new Label(bundle.getString("nextPayment") + ": "  + member.getNextPaymentDate());
        Label contractEndDateLabel = new Label(bundle.getString("contractEnd") + ": " + member.getContractEndDate());
        Label renewedLabel = new Label(bundle.getString("renewed")+ ": " + member.isRenewedMembership());
        Button renewButton = new Button(bundle.getString("renewMem"));
        renewButton.setOnAction(e -> {
            double fee = member.getMembershipType().getMonthlyFee();
            if (member.getBalance() >= fee) {
                member.setBalance(member.getBalance() - fee);
                member.setRenewedMembership(true);
                member.setNextPaymentDate(LocalDate.now().plusMonths(1).toString());

                DatabaseManager.updateMemberPaymentStatus(member);
                DatabaseManager.updateMemberBalance(member);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("renewedMem"));
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("broke"));
                alert.showAndWait();
            }
        });
        Button switchButton = new Button(bundle.getString("switchMem"));
        switchButton.setOnAction(e -> {
            Membership newMembership;
            if (member.getMembershipType() instanceof PremiumMembership){
                newMembership = MembershipFactory.craete("regular");
            }
            else {
                newMembership = MembershipFactory.craete("premium");
            }

            double fee = newMembership.getMonthlyFee();
            if (member.getBalance() >= fee){
                member.setBalance(member.getBalance() - fee);
                member.setMembershipType(newMembership);

                DatabaseManager.updateMemberMembershipType(member);
                DatabaseManager.updateMemberBalance(member);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("switchedMem"));
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("broke"));
                alert.showAndWait();
            }
        });
        Button cancelButton = new Button(bundle.getString("cancelMem"));
        cancelButton.setOnAction(e -> {
            if (member.getBalance() >= 60.00){
                DatabaseManager.deleteMember(member.getLoginUsername());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("cancelledMem"));
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("broke"));
                alert.showAndWait();
            }
        });

        VBox vBox = new VBox(10, membershipTypeLabel, nextPaymentDateLabel, contractEndDateLabel,
                renewedLabel, renewButton, switchButton, cancelButton);

        Scene scene = new Scene(vBox, 300, 300);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("memManager"));
        stage.setScene(scene);
        stage.show();
    }
}
