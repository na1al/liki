package ua.catalog.liki.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(indexes = {
        @Index(name = "idx_tag_alias", columnList = "alias", unique = true),
        @Index(name = "idx_tag_external_id", columnList = "tag_vocabulary_id,externalId", unique = true),
})
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String alias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_vocabulary_id")
    private TagVocabulary vocabulary;

    @Basic
    private Integer externalId;

}