package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_p_epmi_u", columnList = "externalPharmacyId, integration_id", unique = true)
})
public class Pharmacy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "integration_id")
    private Integration integration;

    @Basic
    private int externalPharmacyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Basic
    private String name;

    @Basic
    private String address;

}
