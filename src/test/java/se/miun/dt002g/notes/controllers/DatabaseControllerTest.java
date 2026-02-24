package se.miun.dt002g.notes.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test cases for the DatabaseController class
 * @author rasa2300
 */
public class DatabaseControllerTest {
    private DatabaseController dbController;

    /**
     * Initial setup before running subsequent tests.
     */
    @BeforeEach
    public void setUp() {
        this.dbController = new DatabaseController("testdb_DatabaseModelTest.sqlite");
    }

    /**
     * Destroy the database after each test.
     */
    @AfterEach
    public void tearDown() {
        this.dbController.close();
        this.dbController.destroy();
    }

    // Add test cases for new methods in DatabaseController as they are implemented
}
