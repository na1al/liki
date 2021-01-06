package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MedicineImageSource extends BaseEntity {

    @Id
    private int medicineId;

    @Basic(optional = false)
    private String url;

    @Basic
    private Boolean needUpdate;

}
