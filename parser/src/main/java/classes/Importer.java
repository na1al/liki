package classes;

import java.sql.SQLException;

public interface Importer<F, T> extends Runnable {
    T cast(F dto);
}
