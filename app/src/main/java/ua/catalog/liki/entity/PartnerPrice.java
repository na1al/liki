package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "pharmacyId", "id", "medicineId"})
@Data
@Entity
public class PartnerPrice extends BaseEntity {

    @EmbeddedId
    private Id id;

    @Embedded
    private PharmacyId pharmacyId;

    @Embedded
    private MedicineId medicineId;

    @Basic(optional = false)
    private int price;

    @Basic(optional = false)
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "external_pharmacy_id",  insertable = false, updatable = false),
            @JoinColumn(name = "integration_id",  insertable = false, updatable = false)
    }, foreignKey = @ForeignKey(NO_CONSTRAINT))
    private Pharmacy pharmacy;


    @Data
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "external_medicine_id", insertable = false, updatable = false)
        private int externalMedicineId;
        @Column(name = "external_pharmacy_id", insertable = false, updatable = false)
        private int externalPharmacyId;
        @Column(name = "integration_id", insertable = false, updatable = false)
        private short integrationId;
    }

    @Data
    @Embeddable
    public static class PharmacyId implements Serializable {
        @Column(name = "external_pharmacy_id", insertable = false, updatable = false)
        private int externalPharmacyId;
        @Column(name = "integration_id", insertable = false, updatable = false)
        private short integrationId;
    }

    @Data
    @Embeddable
    public static class MedicineId implements Serializable {
        @Column(name = "external_medicine_id", insertable = false, updatable = false)
        private int externalMedicineId;
        @Column(name = "integration_id", insertable = false, updatable = false)
        private short integrationId;
    }
}
