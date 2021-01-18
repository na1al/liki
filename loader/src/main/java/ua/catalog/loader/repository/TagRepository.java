package ua.catalog.loader.repository;

import ua.catalog.loader.entity.Tag;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TagRepository extends AbstractRepository implements BatchInsert<Tag> {

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
