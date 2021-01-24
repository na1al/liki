package ua.catalog.liki.util.propertyEditor;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.service.TagService;

import java.beans.PropertyEditorSupport;
import java.util.InvalidPropertiesFormatException;
import java.util.Set;
import java.util.stream.Stream;

public class TagPropertyEditor extends PropertyEditorSupport {

    TagService tagService;

    public TagPropertyEditor(TagService tagService) {
        super();
        this.tagService = tagService;
    }


    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        int[] ids = Stream.of(text.split("-")).mapToInt(Integer::parseInt).toArray();


        Set<Tag> tags = tagService.findAllById(ids);

        if (ids.length != tags.size()) {
            throw new IllegalArgumentException("Invalid key property");
        }

        setValue(tags);
    }
}
