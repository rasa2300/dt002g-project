package se.miun.dt002g.notes.models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the DatabaseModel class.
 * @author rasa2300
 */
public class DatabaseModelTest {
    private static DatabaseModel dbModel;

    /**
     * Initial setup before running subsequent tests.
     */
    @BeforeAll
    public static void setUp() {
        dbModel = new DatabaseModel("testdb_DatabaseModelTest.sqlite");
    }

    /**
     * Destroy the database after all tests have run such that it is clean
     * for the next run.
     */
    @AfterAll
    public static void cleanUp() {
        // Implicitly tests close and destroy methods
        dbModel.close();
        dbModel.destroy();
    }

    /**
     * Verifies that a database connection can be retrieved using getConnection()
     */
    @Test
    public void shouldGetDatabaseConnection() {
        assertNotNull(dbModel.getConnection(), "getConnection() should not return null");
    }

    /**
     * Verifies that the resulting database file is written to the JAR's location
     */
    @Test
    public void shouldHaveDatabaseFile() {
        String jarLocation;
        try {
            jarLocation = new File(DatabaseModel.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();
        } catch (URISyntaxException e) {
            fail("JAR location needs to be able to be retrieved");
            // Indicate to Java that jarLocation will always be defined below
            return;
        }
        Path path = Paths.get(jarLocation + "/testdb_DatabaseModelTest.sqlite");

        assertTrue(Files.exists(path), "Database file must exist after it has been created.");
    }
}
