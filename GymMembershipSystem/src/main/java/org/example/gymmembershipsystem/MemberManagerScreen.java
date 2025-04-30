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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MemberManagerScreen extends Application {
    private Employee employee;
    private TableView<Member> memberTable;
    private TextField searchField;
    private ObservableList<Member> memberData;

    public MemberManagerScreen(Employee employee){
        this.employee = employee;
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Member Manager");
        BorderPane layout = new BorderPane();

        Label searchLabel = new Label("Search by: ");

        CheckBox searchByUsername = new CheckBox("Username ");
        CheckBox searchByFirstName = new CheckBox("First Name ");
        CheckBox searchByLastName = new CheckBox("Last Name ");
        CheckBox searchByPhoneNo = new CheckBox("Phone No ");

        searchField = new TextField();
        searchField.setPromptText("Search members...");
        searchField.setOnKeyReleased(e -> {
            String filter = "firstName";
            if (searchByUsername.isSelected())
                filter = "username";
            if (searchByFirstName.isSelected())
                filter = "firstName";
            if (searchByLastName.isSelected())
                filter = "lastName";
            if (searchByPhoneNo.isSelected())
                filter = "phoneNo";

            searchMembers(filter);
        });

        HBox searchBox = new HBox(searchLabel, searchByUsername,searchByFirstName, searchByLastName, searchByPhoneNo, searchField);
        layout.setTop(searchBox);

        memberTable = new TableView<>();
        memberData = FXCollections.observableArrayList();
        memberTable.setItems(memberData);

        TableColumn<Member, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoginUsername()));

        TableColumn<Member, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getfName()));

        TableColumn<Member, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getlName()));

        TableColumn<Member, String> membershipTypeCol = new TableColumn<>("Membership Type");
        membershipTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMembershipType().getType()));

        TableColumn<Member, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNo()));

        TableColumn<Member, Boolean> renewedMemCol = new TableColumn<>("Renewed");
        renewedMemCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isRenewedMembership()));

        TableColumn<Member, String> nextPaymentCol = new TableColumn<>("Next Payment Date");
        nextPaymentCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNextPaymentDate()));

        TableColumn<Member, String> contractEndCol = new TableColumn<>("Contract End Date");
        contractEndCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getContractEndDate()));

        TableColumn<Member, String> latestEntryCol = new TableColumn<>("Latest Entry");
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

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateMember());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteMember());

        Button addButton = new Button("Add New Member");
        addButton.setOnAction(e -> addMember());

        Button refreshTable = new Button("Refresh Table");
        refreshTable.setOnAction(e -> loadAllMembers());

        HBox bottomButtons = new HBox(10, updateButton, deleteButton, addButton, refreshTable);
        layout.setBottom(bottomButtons);

        Scene scene = new Scene(layout, 600, 450);
        stage.setScene(scene);
        stage.show();

        loadAllMembers();
    }

    private void addMember(){
        MemberAddScreen memberAddScreen = new MemberAddScreen();
        Stage stage = new Stage();
        try {
            memberAddScreen.showStage(stage, employee);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loadAllMembers(){
        try {
            List<Member> memberList = DatabaseManager.getAllMembers();
            memberData.clear();
            memberData.addAll(memberList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.showAndWait();
        }
    }

    private void searchMembers(String filter){
        String search = searchField.getText();
        if (search.isEmpty())
            loadAllMembers();
        else {
            List<Member> members = DatabaseManager.searchMembers(filter, search);
            memberData.clear();
            memberData.addAll(members);
        }
    }

    private void updateMember(){
        Member member = memberTable.getSelectionModel().getSelectedItem();
        if (member != null){
            MemberUpdateScreen.showUpdateForm(member, employee);
            loadAllMembers();
        }
    }

    private void deleteMember(){
        Member member = memberTable.getSelectionModel().getSelectedItem();
        if (member != null){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the member?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(input -> {
                if (input == ButtonType.YES){
                    System.out.println("Deleting member");
                    employee.setLatestLog(employee.getLatestLog() + "\n" + "Deleted Member " +
                            member.getfName() + " " + member.getlName());
                    DatabaseManager.updateLogs(employee);
                    DatabaseManager.deleteMember(member.getLoginUsername());
                    loadAllMembers();
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
