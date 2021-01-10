package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.PartnerPrice;
import ua.catalog.liki.entity.Pharmacy;

import java.util.List;

@Repository
public interface PartnerPriceRepository extends JpaRepository<PartnerPrice, PartnerPrice.Id>, PagingAndSortingRepository<PartnerPrice, PartnerPrice.Id> {

    @Query(value = "SELECT pp,p FROM PartnerPrice pp " +
            "INNER JOIN PartnerMedicine pm ON pm.id = pp.medicineId " +
            "INNER JOIN Pharmacy p ON p.id = pp.pharmacyId " +
            "WHERE pm.medicine.id = ?1 AND p.city.id = ?2 " +
            "ORDER BY pp.price ASC ")
    List<PartnerPrice> findAllMedicinePrices(int medicineId, int cityId);

}
