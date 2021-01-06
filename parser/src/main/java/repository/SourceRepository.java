package repository;


import entity.Source;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceRepository extends AbstractRepository {

    public List<Source> findActive() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT id, url, type, active FROM source WHERE active IS TRUE");
        ResultSet rs = ps.executeQuery();


        List<Source> entities = new ArrayList<>();

        while (rs.next()) {
            Source entity = new Source();
            entity.setId(rs.getInt("id"));
            entity.setType(Source.Type.valueOf(rs.getString("type")));
            entity.setUrl(rs.getString("url"));
            entity.setActive(rs.getBoolean("active"));
            entities.add(entity);
        }

        return entities;
    }


}
