package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Source extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Basic
    public String url;

    @Basic
    public Boolean active;

    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        MEDICINE, PARTNER_MEDICINE, PHARMACY
    }

}
