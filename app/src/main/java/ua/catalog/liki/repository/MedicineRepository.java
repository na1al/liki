package ua.catalog.liki.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Medicine;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Integer>, PagingAndSortingRepository<Medicine, Integer> {

}
