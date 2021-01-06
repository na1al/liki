package repository;


import entity.Medicine;
import entity.MedicineImageSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MedicineImageSourceRepository extends AbstractRepository implements BatchInsert<MedicineImageSource> {


    public MedicineImageSource next() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT medicine_id, url, need_update FROM medicine_image_source WHERE need_update IS TRUE LIMIT 1");
        ResultSet rs = ps.executeQuery();

        if(!rs.next()){
            return null;
        }

        MedicineImageSource entity = new MedicineImageSource();
        entity.setMedicineId(rs.getInt("medicine_id"));
        entity.setUrl(rs.getString("url"));
        entity.setNeedUpdate(rs.getBoolean("need_update"));
        return entity;
    }

    public int update(MedicineImageSource source) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
                "UPDATE medicine_image_source SET  url = ? , need_update = ? WHERE medicine_id = ?");

        ps.setString(1,source.getUrl());
        ps.setBoolean(2,source.getNeedUpdate());
        ps.setInt(3,source.getMedicineId());

        return ps.executeUpdate();
    }

    public void batchInsert(List<MedicineImageSource> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine_image_source  (medicine_id, url, need_update) VALUES  (?, ?, true) ON CONFLICT (medicine_id) DO UPDATE " +
                "  SET need_update = CASE WHEN medicine_image_source.url = excluded.url THEN medicine_image_source.need_update ELSE true END, " +
                "      url = excluded.url;");

        connection.setAutoCommit(false);

        for (MedicineImageSource item : items) {
            ps.setInt(1, item.getMedicineId());
            ps.setString(2, item.getUrl());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
