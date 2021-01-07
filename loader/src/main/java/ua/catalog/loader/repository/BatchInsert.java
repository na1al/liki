package ua.catalog.loader.repository;

import java.sql.SQLException;
import java.util.List;

public interface BatchInsert<T> {


    public void batchInsert(List<T> data) throws SQLException;

}
