package org.example.gymmembershipsystem;

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

public class EmployeeManagerScreen {
    private Employee employee;
    private TableView<Employee> employeeTable;
    private TextField searchField;
    private ObservableList<Employee> employeeData;

    public EmployeeManagerScreen(Employee employee){
        this.employee = employee;
    }
    public void start(Stage stage, ResourceBundle bundle) {
        stage.setTitle(bundle.getString("employeeManager"));
        BorderPane layout = new BorderPane();

        Label searchLabel = new Label(bundle.getString("searchBy"));

        CheckBox searchByUsername = new CheckBox(bundle.getString("username"));
        CheckBox searchByFirstName = new CheckBox(bundle.getString("firstName"));
        CheckBox searchByLastName = new CheckBox(bundle.getString("lastName"));

        searchField = new TextField();
        searchField.setPromptText(bundle.getString("searchEmployee"));
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

        TableColumn<Employee, String> usernameCol = new TableColumn<>(bundle.getString("username"));
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoginUsername()));

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>(bundle.getString("firstName"));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getfName()));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>(bundle.getString("lastName"));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getlName()));

        employeeTable.getColumns().add(usernameCol);
        employeeTable.getColumns().add(firstNameColumn);
        employeeTable.getColumns().add(lastNameColumn);

        layout.setCenter(employeeTable);


        Button deleteButton = new Button(bundle.getString("deleteEmployee"));
        deleteButton.setOnAction(e -> deleteEmployee(bundle));

        Button addEmployeeButton = new Button(bundle.getString("employeeAdd"));
        addEmployeeButton.setOnAction(e -> {
            try {
                EmployeeAddScreen.show(bundle);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button refreshButton = new Button(bundle.getString("refresh"));
        refreshButton.setOnAction( e -> loadAllEmployees());

        HBox bottomButtons = new HBox(deleteButton, addEmployeeButton, refreshButton);
        layout.setBottom(bottomButtons);

        Scene scene = new Scene(layout, 450, 300);
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

    private boolean deleteEmployee(ResourceBundle bundle){
        Employee emp = employeeTable.getSelectionModel().getSelectedItem();

        if (employeeTable.getItems().size() == 1){
            Alert oneEmployeeAlert = new Alert(Alert.AlertType.ERROR, bundle.getString("oneEmployee"));
            oneEmployeeAlert.showAndWait();
            return false;
        }

        if (emp.getLoginUsername().equals(employee.getLoginUsername())){
            Alert selfDeleteAlert = new Alert(Alert.AlertType.ERROR, bundle.getString("deleteYs"));
            selfDeleteAlert.showAndWait();
            return false;
        }

        if (emp != null){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    bundle.getString("deleteConfEmp"), ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(input -> {
                if (input == ButtonType.YES){
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
