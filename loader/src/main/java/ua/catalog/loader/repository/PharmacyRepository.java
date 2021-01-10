package ua.catalog.loader.repository;

import ua.catalog.loader.entity.Pharmacy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class PharmacyRepository extends AbstractRepository implements BatchInsert<Pharmacy> {

    public void batchInsert(List<Pharmacy> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO pharmacy (" +
                "city_id, external_pharmacy_id, integration_id, name, phone, schedule, address, lat, lng) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT pharmacy_pkey DO UPDATE " +
                "  SET city_id = excluded.city_id," +
                "      name = excluded.name," +
                "      phone = excluded.phone," +
                "      schedule = excluded.schedule," +
                "      address = excluded.address," +
                "      lat = excluded.lat," +
                "      lng = excluded.lng;");

        connection.setAutoCommit(false);

        for (Pharmacy item : items) {
            ps.setInt(2, item.getPartnerPharmacyId());
            ps.setInt(3, item.getIntegrationId());
            ps.setString(4, item.getName());
            ps.setString(7, item.getAddress());

            if(!item.getPhone().isEmpty()){
                ps.setString(5, item.getPhone());
            }else{
                ps.setNull(5, Types.INTEGER);
            }

            if(!item.getSchedule().isEmpty()){
                ps.setString(6, item.getSchedule());
            }else{
                ps.setNull(6, Types.INTEGER);
            }

            if(item.getLat() != null && item.getLng() != null){
                ps.setDouble(8, item.getLat());
                ps.setDouble(9, item.getLng());
            }else {
                ps.setNull(8, Types.DOUBLE);
                ps.setNull(9, Types.DOUBLE);
            }

            if (item.getCityId() == 0) {
                ps.setNull(1, Types.INTEGER);
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
