package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.entity.TagVocabulary;
import ua.catalog.liki.repository.TagRepository;
import ua.catalog.liki.repository.TagVocabularyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagVocabularyService {


    private final TagVocabularyRepository tagVocabularyRepository;

    public TagVocabularyService(TagVocabularyRepository tagVocabularyRepository) {
        this.tagVocabularyRepository = tagVocabularyRepository;
    }

    public List<TagVocabulary> findAll() {
        return tagVocabularyRepository.findAllByOrderByPriorityAsc();
    }

}
