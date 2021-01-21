package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@JsonIgnoreProperties(value = {"createdAt", "updatedAt"})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TagVocabulary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        CATEGORY, MANUFACTURER, FORM, RECIPE, WHOM
    }

}
