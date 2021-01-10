package ua.catalog.loader.classes;

import java.sql.SQLException;
import java.util.List;

public interface Importer<F, T> extends Runnable {
    T cast(F dto);
    public abstract void batchInsert(List<T> entities) throws SQLException;
}
