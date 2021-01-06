package repository;

import entity.PartnerPrice;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PartnerPriceRepository extends AbstractRepository implements BatchInsert<PartnerPrice> {

    public void batchInsert(List<PartnerPrice> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO partner_price  (external_medicine_id, integration_id, external_pharmacy_id, price, quantity) VALUES  (?, ?, ?, ?, ?) " +
                "ON CONFLICT ON CONSTRAINT partner_price_pkey DO UPDATE " +
                "  SET price = excluded.price, " +
                "      quantity = excluded.quantity;");

        connection.setAutoCommit(false);

        for (PartnerPrice item : items) {
            ps.setInt(1, item.getExternalMedicineId());
            ps.setInt(2, item.getIntegrationId());
            ps.setInt(3, item.getExternalPharmacyId());
            ps.setInt(4, item.getPrice());
            ps.setFloat(5, item.getQuantity());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
