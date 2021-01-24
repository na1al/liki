package ua.catalog.liki.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "medicine_tag")
@Table(indexes = {
        @Index(name = "medicine_tag_tag_id_index", columnList = "tagId")
})
public class MedicineTag implements Serializable {

    @Id
    @Column(insertable = false, updatable = false)
    private int medicineId;

    @Id
    @Column(insertable = false, updatable = false)
    private int tagId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicineId")
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId")
    private Tag tag;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="medicineId", referencedColumnName = "medicineId")
    private List<MedicineTag> selfMedicineTag;
//
//    @Embeddable
//    public static class MedicineTagId implements Serializable {
//
//        @Column(name = "medicine_id")
//        private Long medicineId;
//
//        @Column(name = "tag_id")
//        private Long tagId;
//
//        protected MedicineTagId() {}
//
//        public MedicineTagId (Long postId, Long tagId) {
//            this.medicineId = postId;
//            this.tagId = tagId;
//        }
//    }

}

