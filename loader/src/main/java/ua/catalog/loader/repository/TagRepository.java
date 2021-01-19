package ua.catalog.loader.repository;

import ua.catalog.loader.entity.Tag;
import ua.catalog.loader.entity.TagVocabulary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagRepository extends AbstractRepository implements BatchInsert<Tag> {

    public Map<TagVocabulary.Type, Map<Integer, Integer>> getTagCascadeIndex() throws SQLException {

        Map<TagVocabulary.Type, Map<Integer, Integer>> index = new HashMap<>();

        PreparedStatement ps = connection.prepareStatement("SELECT id, type,  external_id FROM tag " +
                "INNER JOIN tag_vocabulary v ON tag_vocabulary_id = v.id");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            if (!index.containsKey(rs.getString("type"))) {
                index.put(TagVocabulary.Type.valueOf(rs.getString("type")), new HashMap<>());
            }
            index
                    .get(rs.getString("type"))
                    .put(rs.getInt("external_id"), rs.getInt("id"));
        }

        return index;
    }

    public void batchInsert(List<Tag> items) throws SQLException {

        if (items.isEmpty()) {
            return;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO tag  (name, alias, tag_vocabulary_id, external_id) VALUES  (?, ?, ?, ?) ON CONFLICT ON CONSTRAINT idx_tag_external_id DO UPDATE " +
                "  SET name = excluded.name, " +
                "      alias = excluded.alias;");

        connection.setAutoCommit(false);

        for (Tag item : items) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getAlias());
            ps.setInt(3, item.getTagVocabularyId());
            ps.setInt(4, item.getExternalId());
            ps.addBatch();
        }

        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }

}
