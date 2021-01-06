package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
        private long medicine_id;
        private long city_id;
    }

}
