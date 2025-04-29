package org.example.gymmembershipsystem;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

        searchField = new TextField();
        searchField.setPromptText("Search members...");
        searchField.setOnKeyReleased(e -> searchMembers());

        layout.setTop(searchField);

        memberTable = new TableView<>();
        memberData = FXCollections.observableArrayList();
        memberTable.setItems(memberData);

        TableColumn<Member, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoginUsername()));

        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getfName() + " " + cellData.getValue().getlName()));

        TableColumn<Member, String> membershipTypeCol = new TableColumn<>("Membership Type");
        membershipTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMembershipType().getType()));

        memberTable.getColumns().add(usernameCol);
        memberTable.getColumns().add(nameColumn);
        memberTable.getColumns().add(membershipTypeCol);

        layout.setCenter(memberTable);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateMember());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteMember());

        HBox bottomButtons = new HBox(10, updateButton, deleteButton);
        layout.setBottom(bottomButtons);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();

        loadAllMembers();
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

    private void searchMembers(){
        String search = searchField.getText();
        if (search.isEmpty())
            loadAllMembers();
        else {
            List<Member> members = DatabaseManager.searchMembers("firstName", search);
            memberData.clear();
            memberData.addAll(members);
        }

    }

    private void updateMember(){
        Member member = memberTable.getSelectionModel().getSelectedItem();
        if (member != null){
            MemberUpdateScreen.showUpdateForm(member);
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
