package org.example.service;

import org.example.exception.ReservationException;
import org.example.model.Reservation;
import org.example.model.ReservationStatus;
import org.example.model.RestaurantTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ReservationService {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.example.jpa");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void addReservation(Reservation reservation) throws ReservationException {
        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatus.PENDING);
        }
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(reservation);
        transaction.commit();
    }

    public void cancelReservation(Reservation reservation) throws ReservationException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        reservation.setStatus(ReservationStatus.CANCELED);
        entityManager.merge(reservation);
        transaction.commit();
    }

    public List<Reservation> getAllReservations() {
        return entityManager.createQuery("from Reservation", Reservation.class).getResultList();
    }
}