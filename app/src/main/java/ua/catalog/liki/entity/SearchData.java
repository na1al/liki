package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class SearchData implements Serializable {

    @JsonIgnore
    @EmbeddedId
    private Id id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String alias;

    @Basic
    private Integer price;

    @Basic
    private String image;

    @JsonIgnore
    @Basic(optional = false)
    private String normalizedText;


    public enum Type {
        MEDICINE, CATEGORY
    }

    @Data
    @Embeddable
    public static class Id implements Serializable {

        private int entityId;

        @Column(columnDefinition = "varchar(10)")
        @Enumerated(EnumType.STRING)
        private Type type;
    }

}
