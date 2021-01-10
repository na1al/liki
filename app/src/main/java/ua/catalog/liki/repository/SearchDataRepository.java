package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.SearchData;


public interface SearchDataRepository extends JpaRepository<SearchData, SearchData.Id>, PagingAndSortingRepository<SearchData, SearchData.Id> {

    @Query(value = "select * from search_data where normalized_text %>> ?1 order by similarity(normalized_text, ?1) desc limit 10", nativeQuery = true)
    public SearchData[] liveSearch(String query);

}
