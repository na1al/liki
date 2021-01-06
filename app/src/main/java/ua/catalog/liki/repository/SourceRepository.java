package ua.catalog.liki.repository;


import org.springframework.data.repository.CrudRepository;
import ua.catalog.liki.entity.Source;

public interface SourceRepository extends CrudRepository<Source, Integer> {
}
