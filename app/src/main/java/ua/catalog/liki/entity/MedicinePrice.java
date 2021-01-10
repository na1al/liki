package ua.catalog.liki.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties(value = {"id", "city", "createdAt", "updatedAt"})
@Data
@Entity
public class MedicinePrice extends BaseEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;

    @Basic(optional = false)
    private int price;

    @Basic(optional = false)
    private int quantity;

    @Data
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "medicine_id", insertable = false, updatable = false)
        private int medicineId;
        @Column(name = "city_id", insertable = false, updatable = false)
        private int cityId;
    }

}
