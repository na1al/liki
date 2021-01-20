package ua.catalog.loader.repository;

import ua.catalog.loader.entity.TagVocabulary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TagVocabularyRepository extends AbstractRepository {

    public Map<TagVocabulary.Type, Integer> getTagVocabularyIndex() throws SQLException {

        Map<TagVocabulary.Type, Integer> index = new HashMap<>();

        PreparedStatement ps = connection.prepareStatement("SELECT id, type FROM tag_vocabulary");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            index.put(TagVocabulary.Type.valueOf(rs.getString("type")), rs.getInt("id"));
        }
        return index;
    }

}
