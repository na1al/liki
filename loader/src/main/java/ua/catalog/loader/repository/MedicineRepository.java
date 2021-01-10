package ua.catalog.loader.repository;

import ua.catalog.loader.entity.Medicine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MedicineRepository extends AbstractRepository implements BatchInsert<Medicine> {


    public Medicine findById(int id) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM medicine WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) return null;



        Medicine entity = new Medicine();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setDescription(rs.getString("description"));
        entity.setMediaId(rs.getInt("media_id") > 0 ? rs.getInt("media_id") : null);

        return entity;
    }

    public Medicine save(Medicine entity) throws SQLException {

        PreparedStatement ps = null;
        if (entity.getId() > 0) {
            ps = connection.prepareStatement("UPDATE medicine SET name = ?,  description = ?, media_id = ?  WHERE id = ?");
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getMediaId());
            ps.setInt(4, entity.getId());
            ps.execute();
        } else {
            ps = connection.prepareStatement("INSERT INTO medicine (name, description, media_id) VALUES  (?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getMediaId());
            ps.execute();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }

        return entity;
    }

    public void batchInsert(List<Medicine> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine  (id, name, description, alias) VALUES  (?, ?, ?, ?) ON CONFLICT (id) DO UPDATE " +
                "  SET name = excluded.name, " +
                "      description = excluded.description;");

        connection.setAutoCommit(false);

        for (Medicine item : items) {
            ps.setInt(1, item.getId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getAlias());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
