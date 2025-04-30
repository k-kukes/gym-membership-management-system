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

public class EmployeeManagerScreen extends Application {
    private Employee employee;
    private TableView<Employee> employeeTable;
    private TextField searchField;
    private ObservableList<Employee> employeeData;

    public EmployeeManagerScreen(Employee employee){
        this.employee = employee;
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Employee Manager");
        BorderPane layout = new BorderPane();

        Label searchLabel = new Label("Search by: ");

        CheckBox searchByUsername = new CheckBox("Username ");
        CheckBox searchByFirstName = new CheckBox("First Name ");
        CheckBox searchByLastName = new CheckBox("Last Name ");

        searchField = new TextField();
        searchField.setPromptText("Search employees...");
        searchField.setOnKeyReleased(e -> {
            String filter = "firstName";
            if (searchByUsername.isSelected())
                filter = "username";
            if (searchByFirstName.isSelected())
                filter = "firstName";
            if (searchByLastName.isSelected())
                filter = "lastName";

            searchEmployees(filter);
        });

        HBox searchBox = new HBox(searchLabel, searchByUsername,searchByFirstName, searchByLastName, searchField);
        layout.setTop(searchBox);

        employeeTable = new TableView<>();
        employeeData = FXCollections.observableArrayList();
        employeeTable.setItems(employeeData);

        TableColumn<Employee, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoginUsername()));

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getfName()));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getlName()));

        employeeTable.getColumns().add(usernameCol);
        employeeTable.getColumns().add(firstNameColumn);
        employeeTable.getColumns().add(lastNameColumn);

        layout.setCenter(employeeTable);


        Button deleteButton = new Button("Delete Employee");
        deleteButton.setOnAction(e -> deleteEmployee());

        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.setOnAction(e -> {
            try {
                EmployeeAddScreen.show();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox bottomButtons = new HBox(deleteButton, addEmployeeButton);
        layout.setBottom(bottomButtons);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();

        loadAllEmployees();
    }

    private void searchEmployees(String filter){
        String search = searchField.getText();
        if (search.isEmpty())
            loadAllEmployees();
        else {
            List<Employee> employees = DatabaseManager.searchEmployees(filter, search);
            employeeData.clear();
            employeeData.addAll(employees);
        }
    }

    private boolean deleteEmployee(){
        Employee emp = employeeTable.getSelectionModel().getSelectedItem();

        if (employeeTable.getItems().size() == 1){
            Alert oneEmployeeAlert = new Alert(Alert.AlertType.ERROR, "Only 1 Employee, Can't Delete!");
            oneEmployeeAlert.showAndWait();
            return false;
        }

        if (emp == employee){
            Alert selfDeleteAlert = new Alert(Alert.AlertType.ERROR, "Can't delete yourself!");
            selfDeleteAlert.showAndWait();
            return false;
        }

        if (emp != null){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the employee?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(input -> {
                if (input == ButtonType.YES){
                    System.out.println("Deleting employee");
                    DatabaseManager.deleteEmployee(emp.getLoginUsername());
                    loadAllEmployees();
                }
            });
        }

        return true;
    }

    private void loadAllEmployees(){
        try {
            List<Employee> employeeList = DatabaseManager.getAllEmployees();
            employeeData.clear();
            employeeData.addAll(employeeList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.showAndWait();
        }
    }
}
