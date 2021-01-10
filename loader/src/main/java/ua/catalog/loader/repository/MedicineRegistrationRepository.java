package ua.catalog.loader.repository;

import ua.catalog.loader.entity.MedicineRegistration;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MedicineRegistrationRepository extends AbstractRepository implements BatchInsert<MedicineRegistration> {


    public void batchInsert(List<MedicineRegistration> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine_registration  (medicine_id, type, code) VALUES  (?, ?, ?) ON CONFLICT ON CONSTRAINT medicine_registration_pkey DO UPDATE " +
                "  SET code = excluded.code");

        connection.setAutoCommit(false);

        for (MedicineRegistration item : items) {
            ps.setInt(1, item.getMedicineId());
            ps.setString(2, item.getType().toString());
            ps.setString(3, item.getCode());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
