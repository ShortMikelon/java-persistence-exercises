package com.bobocode;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DemoApp {
    @SneakyThrows
    public static void main(String[] args) {
        DataSource dataSource = initDb();
        var start = System.nanoTime();

        for (int i = 0; i < 20; i++) {
            try (var connection = dataSource.getConnection()) {
                try (var selectStatement = connection.createStatement()) {
                    selectStatement.executeQuery("select random()"); // just to call the DB
                }
            }
        }

        var end = System.nanoTime();
        System.out.println((end - start) / 1000_000 + "ms");
    }

    private static DataSource initDb() { // todo: refactor to use a custom pooled data source
        var dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://0.tcp.eu.ngrok.io:10874/postgres");
        dataSource.setUser("bobouser");
        dataSource.setPassword("bobopass");
        return dataSource;
    }
}
