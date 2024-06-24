package org.example.service;

import org.example.model.Reservation;
import org.example.util.GenericSorter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Comparator;
import java.util.List;

public class ReservationService {

    private SessionFactory factory;
    private GenericSorter<Reservation> sorter;

    public ReservationService() {
        factory = new Configuration().configure().buildSessionFactory();
        sorter = new GenericSorter<>();
    }

    public List<Reservation> getAllReservations() {
        Session session = factory.openSession();
        List<Reservation> reservationList = session.createQuery("from Reservation", Reservation.class).list();
        session.close();
        return reservationList;
    }

    public void addReservation(Reservation reservation) throws Exception {
        if (reservation.getStartTime().after(reservation.getEndTime())) {
            throw new Exception("Start time must be before end time.");
        }

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(reservation);
        transaction.commit();
        session.close();
    }

    public void cancelReservation(Reservation reservation) throws Exception {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(reservation);
        transaction.commit();
        session.close();
    }

    public List<Reservation> getReservationsSortedByDate() {
        return sorter.sort(getAllReservations(), Comparator.comparing(Reservation::getReservationDate));
    }

    public List<Reservation> getReservationsSortedByStartTime() {
        return sorter.sort(getAllReservations(), Comparator.comparing(Reservation::getStartTime));
    }
}