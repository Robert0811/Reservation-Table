package org.example.service;

import org.example.model.Reservation;
import org.example.model.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.Assert.*;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private TableService tableService;

    @Before
    public void setUp() {
        reservationService = new ReservationService();
        tableService = new TableService();
        Table table = new Table(1, 4);
        tableService.addTable(table);
    }

    @After
    public void tearDown() {
        // Clean up after each test
    }

    @Test
    public void testAddReservation() {
        Table table = tableService.getAllTables().get(0);
        Reservation reservation = new Reservation(table, "John Doe", Date.valueOf("2023-07-01"), Time.valueOf("12:00:00"), Time.valueOf("14:00:00"));
        try {
            reservationService.addReservation(reservation);
            List<Reservation> reservations = reservationService.getAllReservations();
            assertTrue(reservations.contains(reservation));
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void testCancelReservation() {
        Table table = tableService.getAllTables().get(0);
        Reservation reservation = new Reservation(table, "John Doe", Date.valueOf("2023-07-01"), Time.valueOf("12:00:00"), Time.valueOf("14:00:00"));
        try {
            reservationService.addReservation(reservation);
            reservationService.cancelReservation(reservation);
            List<Reservation> reservations = reservationService.getAllReservations();
            assertFalse(reservations.contains(reservation));
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }
}