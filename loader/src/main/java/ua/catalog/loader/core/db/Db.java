package ua.catalog.loader.core.db;

import lombok.Getter;
import ua.catalog.loader.config.ApplicationConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static Db instance;

    @Getter
    private Connection connection;

    private Db(ApplicationConfig.Db config) throws SQLException {
        connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
    }

    public static Db getInstance() {
        return instance;
    }

    public static Db connect(ApplicationConfig.Db config) throws SQLException {
        instance = new Db(config);
        return instance;
    }

}
