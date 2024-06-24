package org.example.service;

import org.example.model.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TableService {

    private SessionFactory factory;

    public TableService() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public List<Table> getAllTables() {
        Session session = factory.openSession();
        List<Table> tableList = session.createQuery("from Table", Table.class).list();
        session.close();
        return tableList;
    }

    public void addTable(Table table) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(table);
        transaction.commit();
        session.close();
    }
}