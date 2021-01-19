package ua.catalog.liki.core;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class Page extends PageImpl {
    public Page(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public Page(List content) {
        super(content);
    }
}
