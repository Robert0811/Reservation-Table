package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.exception.ReservationException;
import org.example.model.Reservation;
import org.example.model.Table;
import org.example.service.ReservationService;
import org.example.service.TableService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.regex.Pattern;

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
        try {
            int tableNumber = Integer.parseInt(tableNumberField.getText());
            int seats = Integer.parseInt(seatsField.getText());
            Table table = new Table(tableNumber, seats);
            tableService.addTable(table);
            tableView.setItems(getTableList());
        } catch (NumberFormatException e) {
            showError("Invalid input for table number or seats.");
        }
    }

    @FXML
    private void handleReserveTable() {
        try {
            if (!isValidTime(startTimeField.getText()) || !isValidTime(endTimeField.getText())) {
                showError("Invalid time format. Please use HH:mm.");
                return;
            }
            Table selectedTable = tableView.getSelectionModel().getSelectedItem();
            if (selectedTable != null) {
                String reservedBy = reservedByField.getText();
                Date reservationDate = Date.valueOf(reservationDateField.getValue());
                Time startTime = Time.valueOf(startTimeField.getText() + ":00");
                Time endTime = Time.valueOf(endTimeField.getText() + ":00");
                Reservation reservation = new Reservation(selectedTable, reservedBy, reservationDate, startTime, endTime);
                reservationService.addReservation(reservation);
                reservationTableView.setItems(getReservationList());
            }
        } catch (ReservationException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Invalid input or other error.");
        }
    }

    @FXML
    private void handleCancelReservation() {
        try {
            Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                reservationService.cancelReservation(selectedReservation);
                reservationTableView.setItems(getReservationList());
            }
        } catch (ReservationException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidTime(String time) {
        String regex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        return time.matches(regex);
    }
}