package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Medicine extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "medicine", fetch = FetchType.LAZY)
    private List<PartnerMedicine> partnerMedicines = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @Basic
    private String name;

    @Column(columnDefinition="text")
    private String description;

    @Basic
    private Short priority;

}
