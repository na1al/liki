package ua.catalog.loader.repository;

import ua.catalog.loader.entity.PartnerMedicine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PartnerMedicineRepository extends AbstractRepository implements BatchInsert<PartnerMedicine> {

    public void batchInsert(List<PartnerMedicine> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO partner_medicine  (medicine_id, external_medicine_id, integration_id, name, description) VALUES  (?, ?, ?, ?, ?) " +
                "ON CONFLICT ON CONSTRAINT partner_medicine_pkey DO UPDATE " +
                "  SET name = excluded.name, " +
                "      description = excluded.description;");

        connection.setAutoCommit(false);

        for (PartnerMedicine item : items) {

            if (item.getMedicineId() == 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, item.getMedicineId());
            }

            ps.setInt(2, item.getExternalMedicineId());
            ps.setInt(3, item.getIntegrationId());
            ps.setString(4, item.getName());
            ps.setString(5, item.getDescription());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
