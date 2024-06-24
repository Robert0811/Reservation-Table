package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.model.RestaurantTable;
import org.example.service.TableService;

public class MainController {

    @FXML
    private TextField tableNumberField;
    @FXML
    private TextField seatsField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<RestaurantTable> tableView;
    @FXML
    private TableColumn<RestaurantTable, Integer> tableNumberColumn;
    @FXML
    private TableColumn<RestaurantTable, Integer> seatsColumn;

    private TableService tableService;
    private ObservableList<RestaurantTable> tableData;

    @FXML
    private void initialize() {
        tableService = new TableService();
        tableData = FXCollections.observableArrayList(tableService.getAllTables());

        tableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        seatsColumn.setCellValueFactory(cellData -> cellData.getValue().seatsProperty().asObject());

        tableView.setItems(tableData);
    }

    @FXML
    private void handleAddTable() {
        int number = Integer.parseInt(tableNumberField.getText());
        int seats = Integer.parseInt(seatsField.getText());
        RestaurantTable table = new RestaurantTable(number, seats);
        tableService.saveTable(table);
        tableData.add(table);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<RestaurantTable> filteredData = FXCollections.observableArrayList();

        for (RestaurantTable table : tableService.getAllTables()) {
            if (String.valueOf(table.getNumber()).contains(searchText) || String.valueOf(table.getSeats()).contains(searchText)) {
                filteredData.add(table);
            }
        }

        tableView.setItems(filteredData);
    }
}