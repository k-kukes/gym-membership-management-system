package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ResourceBundle;

public class MemberManagerScreen {
    private Employee employee;
    private TableView<Member> memberTable;
    private TextField searchField;
    private ObservableList<Member> memberData;

    public MemberManagerScreen(Employee employee) {
        this.employee = employee;
    }


    public void start(Stage stage, ResourceBundle bundle) {
        stage.setTitle(bundle.getString("memberManager"));
        BorderPane layout = new BorderPane();

        Label searchLabel = new Label(bundle.getString("searchBy"));

        CheckBox searchByUsername = new CheckBox(bundle.getString("username"));
        CheckBox searchByFirstName = new CheckBox(bundle.getString("firstName"));
        CheckBox searchByLastName = new CheckBox(bundle.getString("lastName"));
        CheckBox searchByPhoneNo = new CheckBox(bundle.getString("phoneNo"));

        searchField = new TextField();
        searchField.setPromptText(bundle.getString("searchMembers"));
        searchField.setOnKeyReleased(e -> {
            String filter = "firstName";
            if (searchByUsername.isSelected())
                filter = "username";
            if (searchByFirstName.isSelected()){
                filterByName(memberData);
            }
            if (searchByLastName.isSelected())
                filter = "lastName";
            if (searchByPhoneNo.isSelected())
                filter = "phoneNo";

            searchMembers(filter);
        });

        HBox searchBox = new HBox(searchLabel, searchByUsername, searchByFirstName, searchByLastName, searchByPhoneNo, searchField);
        layout.setTop(searchBox);

        memberTable = new TableView<>();
        memberData = FXCollections.observableArrayList();
        memberTable.setItems(memberData);

        TableColumn<Member, String> usernameCol = new TableColumn<>(bundle.getString("username"));
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoginUsername()));

        TableColumn<Member, String> firstNameColumn = new TableColumn<>(bundle.getString("firstName"));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getfName()));

        TableColumn<Member, String> lastNameColumn = new TableColumn<>(bundle.getString("lastName"));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getlName()));

        TableColumn<Member, String> membershipTypeCol = new TableColumn<>(bundle.getString("memType"));
        membershipTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMembershipType().getType()));

        TableColumn<Member, String> phoneCol = new TableColumn<>(bundle.getString("phoneNo"));
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNo()));

        TableColumn<Member, Boolean> renewedMemCol = new TableColumn<>(bundle.getString("renewed"));
        renewedMemCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isRenewedMembership()));

        TableColumn<Member, String> nextPaymentCol = new TableColumn<>(bundle.getString("nextPayment"));
        nextPaymentCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNextPaymentDate()));

        TableColumn<Member, String> contractEndCol = new TableColumn<>(bundle.getString("contractEnd"));
        contractEndCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getContractEndDate()));

        TableColumn<Member, String> latestEntryCol = new TableColumn<>(bundle.getString("latestEntry"));
        latestEntryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLatestEntry()));

        memberTable.getColumns().add(usernameCol);
        memberTable.getColumns().add(firstNameColumn);
        memberTable.getColumns().add(lastNameColumn);
        memberTable.getColumns().add(phoneCol);
        memberTable.getColumns().add(membershipTypeCol);
        memberTable.getColumns().add(renewedMemCol);
        memberTable.getColumns().add(nextPaymentCol);
        memberTable.getColumns().add(contractEndCol);
        memberTable.getColumns().add(latestEntryCol);

        layout.setCenter(memberTable);

        Button updateButton = new Button(bundle.getString("update"));
        updateButton.setOnAction(e -> updateMember(bundle));

        Button deleteButton = new Button(bundle.getString("delete"));
        deleteButton.setOnAction(e -> deleteMember(bundle));

        Button addButton = new Button(bundle.getString("addMember"));
        addButton.setOnAction(e -> addMember(bundle));

        Button refreshTable = new Button(bundle.getString("refresh"));
        refreshTable.setOnAction(e -> loadAllMembers());

        HBox bottomButtons = new HBox(10, updateButton, deleteButton, addButton, refreshTable);
        layout.setBottom(bottomButtons);

        Scene scene = new Scene(layout, 600, 450);
        stage.setScene(scene);
        stage.show();

        loadAllMembers();
    }

    private void addMember(ResourceBundle bundle) {
        MemberAddScreen memberAddScreen = new MemberAddScreen();
        Stage stage = new Stage();
        try {
            memberAddScreen.showStage(stage, employee, bundle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadAllMembers() {
        try {
            List<Member> memberList = DatabaseManager.getAllMembers();
            memberData.clear();
            memberData.addAll(memberList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private void searchMembers(String filter) {
        String search = searchField.getText();
        if (search.isEmpty())
            loadAllMembers();
        else {
            List<Member> members = DatabaseManager.searchMembers(filter, search);
            memberData.clear();
            memberData.addAll(members);
        }
    }

    private void updateMember(ResourceBundle bundle) {
        Member member = memberTable.getSelectionModel().getSelectedItem();
        if (member != null) {
            MemberUpdateScreen.showUpdateForm(member, employee, bundle);
            loadAllMembers();
        }
    }

    private void deleteMember(ResourceBundle bundle) {
        Member member = memberTable.getSelectionModel().getSelectedItem();
        if (member != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    bundle.getString("deleteMemberConf"), ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(input -> {
                if (input == ButtonType.YES) {
                    employee.setLatestLog(employee.getLatestLog() + "\n" + "Deleted Member " +
                            member.getfName() + " " + member.getlName());
                    DatabaseManager.updateLogs(employee);
                    DatabaseManager.deleteMember(member.getLoginUsername());
                    loadAllMembers();
                }
            });
        }
    }

    private void filterByName(List<Member> members){
        List<Member> result = members.stream()
                .filter(member -> member.getfName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .toList();
        memberData.clear();
        memberData.addAll(result);
    }
}