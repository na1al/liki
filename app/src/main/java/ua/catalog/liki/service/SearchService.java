package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.SearchData;
import ua.catalog.liki.helper.SearchHelper;
import ua.catalog.liki.repository.SearchDataRepository;

@Service
public class SearchService {

    protected SearchDataRepository searchDataRepository;

    SearchService(SearchDataRepository searchDataRepository) {
        this.searchDataRepository = searchDataRepository;
    }

    public SearchData[] live(String search) {

        String query = SearchHelper.normalize(search);

        return searchDataRepository.liveSearch(query);
    }

}
