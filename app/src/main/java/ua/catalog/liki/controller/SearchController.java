package ua.catalog.liki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.SearchData;
import ua.catalog.liki.service.SearchService;


@RestController
@RequestMapping("/v1")
public class SearchController {

    private SearchService searchService;

    SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/live")
    public Response<SearchData[]> search(@RequestParam(value = "s") String search, Response<SearchData[]> model) {
        model.data = searchService.live(search);
        return model;
    }

}
