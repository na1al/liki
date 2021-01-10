package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "city"})
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Pharmacy extends BaseEntity implements Serializable {

    @JsonIgnore
    @EmbeddedId
    private PharmacyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Basic(optional = false)
    private String name;

    @Basic
    private String phone;

    @Basic
    private String schedule;

    @Basic(optional = false)
    private String address;

    @Basic
    private Double lat;

    @Basic
    private Double lng;

    @Data
    @Embeddable
    public static class PharmacyId implements Serializable {
        @Column(name = "external_pharmacy_id")
        private int externalPharmacyId;
        @Column(name = "integration_id")
        private short integrationId;
    }

}
