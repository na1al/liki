package repository;

import entity.Media;
import entity.MedicineImageSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MediaRepository extends AbstractRepository {


    public Media save(Media entity) throws SQLException {

        PreparedStatement ps = null;
        if (entity.getId() > 0) {
            ps = connection.prepareStatement("UPDATE media SET name = ?,  url = ?  WHERE id = ?");
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getUrl());
            ps.setInt(3, entity.getId());
            ps.execute();
        } else {
            ps = connection.prepareStatement("INSERT INTO media  (name, url) VALUES  (?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getUrl());
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


    public int update(MedicineImageSource source) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
                "UPDATE medicine_image_source SET  url = ? , need_update = ? WHERE medicine_id = ?");

        ps.setString(1, source.getUrl());
        ps.setBoolean(2, source.getNeedUpdate());
        ps.setInt(3, source.getId());

        return ps.executeUpdate();
    }

}
