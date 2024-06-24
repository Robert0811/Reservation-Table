package org.example.service;

import org.example.model.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TableServiceTest {

    private TableService tableService;

    @Before
    public void setUp() {
        tableService = new TableService();
    }

    @After
    public void tearDown() {
        // Clean up after each test
    }

    @Test
    public void testAddTable() {
        Table table = new Table(1, 4);
        tableService.addTable(table);
        List<Table> tables = tableService.getAllTables();
        assertTrue(tables.contains(table));
    }

    @Test
    public void testGetAllTables() {
        Table table = new Table(1, 4);
        tableService.addTable(table);
        List<Table> tables = tableService.getAllTables();
        assertNotNull(tables);
        assertFalse(tables.isEmpty());
    }
}