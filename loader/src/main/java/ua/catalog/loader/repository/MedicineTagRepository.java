package ua.catalog.loader.repository;

import ua.catalog.loader.entity.MedicineTag;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MedicineTagRepository extends AbstractRepository implements BatchInsert<MedicineTag> {

    public void batchInsert(List<MedicineTag> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine_tag  (medicine_id, tag_id) VALUES  (?, ?) ON CONFLICT ON CONSTRAINT medicine_tag_pkey DO NOTHING ");

        connection.setAutoCommit(false);

        for (MedicineTag item : items) {
            ps.setInt(1, item.getMedicineId());
            ps.setInt(2, item.getTagId());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
