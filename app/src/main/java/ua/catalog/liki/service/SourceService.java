package ua.catalog.liki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.catalog.liki.repository.SourceRepository;
import ua.catalog.liki.entity.Source;

@Service
public class SourceService {

    protected SourceRepository sourceRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository){
        this.sourceRepository = sourceRepository;
    }

    /**
     *
     * @return
     */
    public Source getNextSource(){
        return sourceRepository.findById(1).orElse(null);
    }

}
