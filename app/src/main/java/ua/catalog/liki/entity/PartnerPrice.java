package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class PartnerPrice extends BaseEntity {

    @EmbeddedId
    private Id id;

    @Basic(optional = false)
    private int price;

    @Basic(optional = false)
    private int quantity;

    @Data
    @Embeddable
    public static class Id implements Serializable {
        private long external_medicine_id;
        private long integration_id;
        private long external_pharmacy_id;
    }

}
