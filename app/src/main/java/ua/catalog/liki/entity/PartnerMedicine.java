package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_pm_medicine_id", columnList = "medicine_id")
})
public class PartnerMedicine extends BaseEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Basic(optional = false)
    private String name;

    @Column(columnDefinition="text")
    private String description;

    @Data
    @Embeddable
    public static class Id implements Serializable {
        private long external_medicine_id;
        private long integration_id;
    }

}
