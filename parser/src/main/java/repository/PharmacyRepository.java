package repository;

import entity.Pharmacy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PharmacyRepository extends AbstractRepository implements BatchInsert<Pharmacy> {

    public void batchInsert(List<Pharmacy> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO pharmacy (city_id, external_pharmacy_id, integration_id, name, address) VALUES  (?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT idx_p_epmi_u DO UPDATE " +
                "  SET city_id = excluded.city_id," +
                "      name = excluded.name," +
                "      address = excluded.address;");

        connection.setAutoCommit(false);

        for (Pharmacy item : items) {
            ps.setInt(2, item.getPartnerPharmacyId());
            ps.setInt(3, item.getIntegrationId());
            ps.setString(4, item.getName());
            ps.setString(5, item.getAddress());

            if (item.getCityId() == 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, item.getCityId());
            }

            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
