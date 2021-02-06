package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SearchData implements Serializable {

    public enum Type {
        MEDICINE, CATEGORY
    }

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

    @org.hibernate.annotations.Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Tag> tags;

    @Data
    @Embeddable
    public static class Id implements Serializable {

        private int entityId;

        @Column(columnDefinition = "varchar(10)")
        @Enumerated(EnumType.STRING)
        private Type type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag implements Serializable {
        private String name;
        private String alias;
    }

}
