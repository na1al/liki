package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Medicine;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer>, JpaSpecificationExecutor<Medicine>, PagingAndSortingRepository<Medicine, Integer> {

    public Optional<Medicine> findOneById(int id);

    @EntityGraph(attributePaths = {"media", "tag.vocabulary"})
    public Optional<Medicine> findOneByAlias(String alias);

    @EntityGraph(attributePaths = {"media"})
    List<Medicine> findTop10ByOrderByPriorityDesc();

}
