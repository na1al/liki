package ua.catalog.loader.repository;

import ua.catalog.loader.entity.Source;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceRepository extends AbstractRepository {

    public List<Source> findActive() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT id, url, type, active, last_modified FROM source WHERE active IS TRUE");
        ResultSet rs = ps.executeQuery();


        List<Source> entities = new ArrayList<>();

        while (rs.next()) {
            Source entity = new Source();
            entity.setId(rs.getInt("id"));
            entity.setType(Source.Type.valueOf(rs.getString("type")));
            entity.setUrl(rs.getString("url"));
            entity.setActive(rs.getBoolean("active"));
            entity.setLastModified(rs.getLong("last_modified"));
            entities.add(entity);
        }

        return entities;
    }

    public boolean update(Source entity) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE source SET last_modified = ?  WHERE id = ?");
        ps.setLong(1, entity.getLastModified());
        ps.setInt(2, entity.getId());
        return ps.execute();
    }


}
