package repository;

import core.db.Db;

import java.sql.Connection;

public abstract class AbstractRepository {

    protected Connection connection;

    public AbstractRepository() {
        connection = Db.getInstance().getConnection();
    }
}
