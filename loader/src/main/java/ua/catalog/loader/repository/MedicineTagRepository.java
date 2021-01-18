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

        PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine_tags  (tag_vocabulary_id, external_id) VALUES  (?, ?, ?, ?) ON CONFLICT ON CONSTRAINT idx_tag_external_id DO UPDATE " +
                "  SET name = excluded.name, " +
                "      alias = excluded.alias;");

        connection.setAutoCommit(false);

        for (MedicineTag item : items) {
            ps.setInt(3, item.getTagVocabularyId());
            ps.setInt(4, item.getExternalId());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
