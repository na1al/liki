package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.catalog.liki.entity.MedicineViews;


@Repository
public interface MedicineViewsRepository extends CrudRepository<MedicineViews, Integer> {

    @Modifying
    @Query(value = "INSERT INTO medicine_views(medicine_id, all_period, week) " +
            "VALUES (:id, 1, 1) " +
            "ON CONFLICT (medicine_id) DO UPDATE " +
            "SET all_period = medicine_views.all_period + 1, week = medicine_views.week + 1 ",
            nativeQuery = true)
    @Transactional
    public void updateCounter(@Param("id") int id);

}
