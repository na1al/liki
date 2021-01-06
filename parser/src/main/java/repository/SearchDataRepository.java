package repository;

import component.Transliterator;
import entity.Medicine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SearchDataRepository extends AbstractRepository {


    public static final String SEARCH_TYPE_MEDICINE = "MEDICINE";
    public static final int SEARCH_DEFAULT_CITY_ID = 1116;

    public void indexSearchData() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT m.id, m.name, media.name as image, mp.price as price FROM medicine m " +
                "LEFT JOIN media on media.id = m.media_id " +
                "LEFT JOIN medicine_price mp on mp.medicine_id = m.id AND mp.city_id = " + SEARCH_DEFAULT_CITY_ID);
        ResultSet rs = ps.executeQuery();

        ps = connection.prepareStatement("INSERT INTO search_data  (entity_id, type, name, image, price, normalized_text) VALUES  (?, ?, ?, ?, ?, ?)" +
                " ON CONFLICT ON CONSTRAINT search_data_pkey DO UPDATE " +
                "  SET name = excluded.name, " +
                "       image = excluded.image, " +
                "       price = excluded.price, " +
                "       normalized_text = excluded.normalized_text");

        connection.setAutoCommit(false);

        while (rs.next()) {
            ps.setInt(1, rs.getInt("id"));
            ps.setString(2, SEARCH_TYPE_MEDICINE);
            ps.setString(3, rs.getString("name"));
            ps.setString(4, rs.getString("image"));
            if(rs.getInt("price") > 0) ps.setInt(5, rs.getInt("price"));
            else ps.setNull(5, java.sql.Types.INTEGER);
            ps.setString(6, Transliterator.searchNormalize(rs.getString("name")));
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
