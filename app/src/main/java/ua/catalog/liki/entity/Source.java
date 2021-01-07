package ua.catalog.liki.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Source extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String url;

    @Basic
    private Boolean active;

    @Basic
    private Long lastModified;

    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        MEDICINE, PARTNER_MEDICINE, PHARMACY
    }

}
