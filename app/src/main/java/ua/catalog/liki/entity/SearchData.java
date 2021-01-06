package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class SearchData implements Serializable {

    @EmbeddedId
    private Id interactionId;

    @Basic(optional = false)
    private String name;

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

        private long entityId;

        @Column(columnDefinition = "varchar(10)")
        @Enumerated(EnumType.STRING)
        private Type type;
    }

}
