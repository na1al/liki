package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class MedicineRegistration {

    @EmbeddedId
    private Id id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "medicine_id", updatable = false, insertable = false)
//    private Medicine medicine;

    @Basic(optional = false)
    @Column(columnDefinition = "varchar(30)")
    private String code;

    public enum Type {
        UA, MORION, OTIMA, BADM
    }

    @Data
    @Embeddable
    public static class Id implements Serializable {

        private int medicine_id;

        @Column(columnDefinition = "varchar(10)")
        @Enumerated(EnumType.STRING)
        private Type type;
    }

}
