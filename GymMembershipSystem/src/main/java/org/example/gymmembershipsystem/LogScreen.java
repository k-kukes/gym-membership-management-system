package org.example.gymmembershipsystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogScreen {
    private TableView<String> logsTable;
    private ObservableList<String> logsData;

    public void showLogScreen(Employee employee){
        Label titleLabel = new Label("Employee " + employee.getfName() + " " + employee.getlName() + " activity");
        logsTable = new TableView<>();

        TableColumn<String, String> logCol = new TableColumn<>("Activity Message");
        logCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        logCol.setPrefWidth(500);
        logsTable.getColumns().add(logCol);

        List<String> logsList = DatabaseManager.getLogs(employee.getLoginUsername());
        logsData = FXCollections.observableArrayList(logsList);

        logsTable.setItems(logsData);

        Stage stage = new Stage();
        VBox layout = new VBox(10, titleLabel, logsTable);
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Employee Logs");
        stage.show();
    }
}
