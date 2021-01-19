package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.TagRepository;
import java.util.Optional;

@Service
public class TagService {


    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> findByAlias(String alias) {
        return tagRepository.findOneByAlias(alias);
    }
}
