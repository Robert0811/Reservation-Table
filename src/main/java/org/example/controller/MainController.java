package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import org.example.model.RestaurantTable;
import org.example.service.TableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {

    @FXML
    private TextField tableNumberField;

    @FXML
    private TextField seatsField;

    @FXML
    private TableView<RestaurantTable> tableView;

    @FXML
    private TableColumn<RestaurantTable, Integer> tableNumberColumn;

    @FXML
    private TableColumn<RestaurantTable, Integer> seatsColumn;

    private TableService tableService;

    public MainController() {
        this.tableService = new TableService();
    }

    @FXML
    private void initialize() {
        tableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        seatsColumn.setCellValueFactory(cellData -> cellData.getValue().seatsProperty().asObject());
        tableView.setItems(getTableList());
    }

    @FXML
    private void handleAddTable() {
        int number = Integer.parseInt(tableNumberField.getText());
        int seats = Integer.parseInt(seatsField.getText());
        RestaurantTable table = new RestaurantTable(number, seats);
        tableService.saveTable(table);
        tableView.setItems(getTableList());
    }

    private ObservableList<RestaurantTable> getTableList() {
        return FXCollections.observableArrayList(tableService.getAllTables());
    }
}