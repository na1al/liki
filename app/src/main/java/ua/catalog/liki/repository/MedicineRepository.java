package ua.catalog.liki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Medicine;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer>, PagingAndSortingRepository<Medicine, Integer> {

    public Optional<Medicine> findOneById(int id);

    @EntityGraph(attributePaths = {"media", "tag.vocabulary"})
    public Optional<Medicine> findOneByAlias(String alias);

    @EntityGraph(attributePaths = {"media"})
    @Query(value = "SELECT m FROM Medicine m ORDER BY m.priority DESC ", countQuery = "SELECT count(m) FROM Medicine m ")
    Page<Medicine> findAllCatalog(Pageable pageable);

    @EntityGraph(attributePaths = {"media"})
    @Query(value = "SELECT m FROM Medicine m INNER JOIN m.tag t WHERE t.id=:tid ORDER BY m.priority DESC ", countQuery = "SELECT count(m) FROM Medicine m ")
    Page<Medicine> findAllCatalog(@Param("tid") int tid, Pageable pageable);

    @EntityGraph(attributePaths = {"media"})
    List<Medicine> findTop10ByOrderByPriorityDesc();

    @EntityGraph(attributePaths = {"media"})
    List<Medicine> findTop10ByOrderByPriorityDescMediaDesc();

}
