package ua.catalog.liki.controller;

import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.TagVocabulary;
import ua.catalog.liki.service.TagVocabularyService;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class VocabularyController {

    private final TagVocabularyService tagVocabularyService;

    public VocabularyController(TagVocabularyService tagVocabularyService) {
        this.tagVocabularyService = tagVocabularyService;
    }

    @GetMapping("/vocabulary")
    public Response<List<TagVocabulary>> filter(Response<List<TagVocabulary>> model) {
        model.data = tagVocabularyService.findAll();
        return model;
    }

}
