package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Reservation;
import org.example.model.Table;
import org.example.service.ReservationService;
import org.example.service.TableService;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class MainController {

    @FXML
    private TextField tableNumberField;
    @FXML
    private TextField seatsField;
    @FXML
    private TextField reservedByField;
    @FXML
    private DatePicker reservationDateField;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;

    @FXML
    private TableView<Table> tableView;
    @FXML
    private TableColumn<Table, Integer> tableNumberColumn;
    @FXML
    private TableColumn<Table, Integer> seatsColumn;

    @FXML
    private TableView<Reservation> reservationTableView;
    @FXML
    private TableColumn<Reservation, String> reservedByColumn;
    @FXML
    private TableColumn<Reservation, Date> reservationDateColumn;
    @FXML
    private TableColumn<Reservation, Time> startTimeColumn;
    @FXML
    private TableColumn<Reservation, Time> endTimeColumn;

    private TableService tableService;
    private ReservationService reservationService;

    public MainController() {
        tableService = new TableService();
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        reservedByColumn.setCellValueFactory(new PropertyValueFactory<>("reservedBy"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        tableView.setItems(getTableList());
        reservationTableView.setItems(getReservationList());
    }

    private ObservableList<Table> getTableList() {
        List<Table> tables = tableService.getAllTables();
        return FXCollections.observableArrayList(tables);
    }

    private ObservableList<Reservation> getReservationList() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return FXCollections.observableArrayList(reservations);
    }

    @FXML
    private void handleAddTable() {
        int tableNumber = Integer.parseInt(tableNumberField.getText());
        int seats = Integer.parseInt(seatsField.getText());
        Table table = new Table(tableNumber, seats);
        tableService.addTable(table);
        tableView.setItems(getTableList());
    }

    @FXML
    private void handleReserveTable() {
        Table selectedTable = tableView.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            String reservedBy = reservedByField.getText();
            Date reservationDate = Date.valueOf(reservationDateField.getValue());
            Time startTime = Time.valueOf(startTimeField.getText());
            Time endTime = Time.valueOf(endTimeField.getText());
            Reservation reservation = new Reservation(selectedTable, reservedBy, reservationDate, startTime, endTime);
            reservationService.addReservation(reservation);
            reservationTableView.setItems(getReservationList());
        }
    }

    @FXML
    private void handleCancelReservation() {
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationService.cancelReservation(selectedReservation);
            reservationTableView.setItems(getReservationList());
        }
    }
}