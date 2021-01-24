package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import ua.catalog.liki.view.MedicineView;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(value = {"medicineTag", "priority", "createdAt", "updatedAt"})
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_medicine_alias", columnList = "alias", unique = true),
        @Index(name = "idx_medicine_priority", columnList = "priority")
})
public class Medicine extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @JsonView(MedicineView.List.class)
    @OneToMany
    @BatchSize(size = 20)
    @JoinColumn(name = "medicine_id")
    @Where(clause = "city_id=" + City.DEFAULT_CITY_ID)
    private Set<MedicinePrice> prices = new HashSet<>();

    @JsonView({MedicineView.View.class, MedicineView.List.class})
    @OneToMany
    @BatchSize(size = 20)
    @JoinColumn(name = "medicine_id")
    private Set<MedicineRegistration> registrations = new HashSet<>();

    @Basic(optional = false)
    public String name;

    @Basic
    private String alias;

    @JsonView(MedicineView.View.class)
    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "int NOT NULL DEFAULT 0")
    private int priority;

//    @JsonView({MedicineView.View.class})
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<Tag> tag = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicineTag> medicineTag = new HashSet<>();
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            joinColumns = { @JoinColumn(name = "medicine_id") },
//            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
//    private Set<Tag> tag = new HashSet<>();

//    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(
//            joinColumns = { @JoinColumn(name = "medicine_id") },
//            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
//    private Set<Tag> tag = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "medicineId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tag = new ArrayList<>();

}
