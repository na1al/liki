package ua.catalog.liki.entity;

import lombok.Data;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;


@Data
@Entity
public class MedicineViews {

    @Id
    private int medicine_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", updatable = false, insertable = false)
    private Medicine medicine;

    @Basic(optional = false)
    public long allPeriod;

    @Basic(optional = false)
    public long week;


    public MedicineViews(Medicine medicine) {
        this.medicine_id = medicine.getId();
    }

    public MedicineViews() {

    }
}
