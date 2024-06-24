package org.example.service;

import org.example.model.RestaurantTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TableService {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public void saveTable(RestaurantTable table) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(table);
        session.getTransaction().commit();
        session.close();
    }

    public List<RestaurantTable> getAllTables() {
        Session session = sessionFactory.openSession();
        List<RestaurantTable> tables = session.createQuery("from RestaurantTable", RestaurantTable.class).list();
        session.close();
        return tables;
    }
}