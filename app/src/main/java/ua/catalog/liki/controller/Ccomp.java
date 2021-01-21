package ua.catalog.liki.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.catalog.liki.entity.Tag;

@Component
public class Ccomp implements Converter<String, Tag[]> {
    @Override
    public Tag[] convert(String source) {
        return new Tag[0];
    }
}
