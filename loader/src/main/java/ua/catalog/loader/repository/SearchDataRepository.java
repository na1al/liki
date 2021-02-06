package ua.catalog.loader.repository;

import org.postgresql.util.PGobject;
import ua.catalog.loader.component.Transliterator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchDataRepository extends AbstractRepository {


    public static final String SEARCH_TYPE_MEDICINE = "MEDICINE";
    public static final int SEARCH_DEFAULT_CITY_ID = 1116;
    public static final int SEARCH_CATEGORY_ID = 1;

    public void indexSearchData() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT " +
                " m.id, m.name, media.name as image, mp.price as price, m.alias, " +
                " json_agg(json_build_object('name', tag.name, 'alias', tag.alias)) FILTER (WHERE tag.id IS NOT NULL)::jsonb as tags " +
                " FROM medicine m " +
                " LEFT JOIN medicine_tag ON m.id = medicine_tag.medicine_id " +
                " LEFT JOIN tag on medicine_tag.tag_id = tag.id AND tag.tag_vocabulary_id = " + SEARCH_CATEGORY_ID +
                " LEFT JOIN media on media.id = m.media_id " +
                " LEFT JOIN medicine_price mp on mp.medicine_id = m.id AND mp.city_id = " + SEARCH_DEFAULT_CITY_ID +
                " GROUP BY m.id, m.name, media.name, mp.price, m.alias");
        ResultSet rs = ps.executeQuery();

        ps = connection.prepareStatement("INSERT INTO search_data  (entity_id, type, name, image, price, alias, normalized_text, tags) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)" +
                " ON CONFLICT ON CONSTRAINT search_data_pkey DO UPDATE " +
                "  SET name = excluded.name, " +
                "       image = excluded.image, " +
                "       price = excluded.price, " +
                "       alias = excluded.alias, " +
                "       normalized_text = excluded.normalized_text, " +
                "       tags = excluded.tags");

        connection.setAutoCommit(false);

        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");


        while (rs.next()) {
            jsonObject.setValue(rs.getString("tags"));
            ps.setInt(1, rs.getInt("id"));
            ps.setString(2, SEARCH_TYPE_MEDICINE);
            ps.setString(3, rs.getString("name"));
            ps.setString(4, rs.getString("image"));
            if (rs.getInt("price") > 0) ps.setInt(5, rs.getInt("price"));
            else ps.setNull(5, java.sql.Types.INTEGER);
            ps.setString(6, rs.getString("alias"));
            ps.setString(7, Transliterator.searchNormalize(rs.getString("name")));
            ps.setObject(8, jsonObject);
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
