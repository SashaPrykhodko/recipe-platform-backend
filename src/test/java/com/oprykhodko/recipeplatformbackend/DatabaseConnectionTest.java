package com.oprykhodko.recipeplatformbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(TestDatabaseConfiguration.class)
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void shouldConnectToTestContainer() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT version()");

            assert resultSet.next();
            var version = resultSet.getString(1);
            assertTrue(version.contains("PostgreSQL"),
                    "Expected PostgreSQL, but got: " + version);        }
    }

    @Test
    void shouldHaveRunFlywayMigrations() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
            );

            var tableNames = new ArrayList<String>();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("table_name"));
            }

            assertTrue(tableNames.contains("users"), "Users table should exist");
            assertTrue(tableNames.contains("recipes"), "Recipes table should exist");
            assertTrue(tableNames.contains("flyway_schema_history"), "Flyway history should exist");
        }
    }
}
