package org.example.service;

import org.example.model.Reservation;
import org.example.model.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReservationService {

    private SessionFactory factory;

    public ReservationService() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public List<Reservation> getAllReservations() {
        Session session = factory.openSession();
        List<Reservation> reservationList = session.createQuery("from Reservation", Reservation.class).list();
        session.close();
        return reservationList;
    }

    public void addReservation(Reservation reservation) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(reservation);
        transaction.commit();
        session.close();
    }

    public void cancelReservation(Reservation reservation) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(reservation);
        transaction.commit();
        session.close();
    }
}