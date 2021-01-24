package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.catalog.liki.view.TagView;

import javax.persistence.*;

@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "externalId"})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(indexes = {
        @Index(name = "idx_tag_alias", columnList = "alias", unique = true),
        @Index(name = "idx_tag_external_id", columnList = "tagVocabularyId, externalId", unique = true),
})
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String alias;

    @Basic
    private int tagVocabularyId;

    @JsonView({TagView.View.class})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tagVocabularyId", insertable = false, updatable = false)
    private TagVocabulary vocabulary;

    @Basic
    private Integer externalId;

}
