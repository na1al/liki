package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.TagVocabulary;

@Repository
public interface TagVocabularyRepository extends JpaRepository<TagVocabulary, Integer> {

}
