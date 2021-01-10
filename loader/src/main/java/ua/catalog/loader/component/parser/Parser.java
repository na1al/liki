package ua.catalog.loader.component.parser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser<T> {

    @Getter
    CsvToBean<T> reader;

    @Setter
    @Getter
    @Accessors(chain = true)
    private Filter<T> filter = entity -> true;

    public Parser(Class<T> target, String url) throws FileNotFoundException {
        reader = new CsvToBeanBuilder<T>(new FileReader(url))
                .withType(target)
                .build();
    }

    public interface Filter<T>{
        boolean check(T entity);
    }

}
