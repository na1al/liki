package ua.catalog.liki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.PartnerMedicine;
import ua.catalog.liki.entity.Pharmacy;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer>, PagingAndSortingRepository<Pharmacy, Integer> {


//    @Query(value = "SELECT p FROM Pharmacy p INNER JOIN PartnerPrice pp ON pp.id.external_pharmacy_id = p.externalPharmacyId AND pp.id.external_medicine_id = ?1 AND p.city.id = ?2")
//    List<Pharmacy> findAllByMedicine(int medicineId, int cityId);

}
