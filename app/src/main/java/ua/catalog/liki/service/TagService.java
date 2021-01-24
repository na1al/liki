package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagService {


    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> findByAlias(String alias) {
        return tagRepository.findOneByAlias(alias);
    }

    public Set<Tag> findAllById(int[] id){
        return tagRepository.findAllByIdIn(id);
    }

}
