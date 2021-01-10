package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.*;
import ua.catalog.liki.views.MedicineView;
import ua.catalog.liki.views.View;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;
import static javax.persistence.ConstraintMode.PROVIDER_DEFAULT;

@JsonIgnoreProperties(value = {"priority","createdAt", "updatedAt"})
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_medicine_alias", columnList = "alias", unique = true)
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

    @JsonView(MedicineView.View.class)
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

    @Basic
    private Short priority;

}
