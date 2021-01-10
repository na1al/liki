package ua.catalog.loader.classes;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ua.catalog.loader.component.parser.Parser;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class AbstractImporter<F, T> implements Importer<F, T> {

    private static final int BATCH_SIZE = 1000;

    Parser<F> parser;

    List<T> entities = new ArrayList<>(BATCH_SIZE);

    public AbstractImporter(Parser<F> parser) {
        this.parser = parser;
    }

    @SneakyThrows
    public void run() {
        int iteration = 0;
        for (var entity : parser.getReader()) {

            if (!parser.getFilter().check(entity)) continue;

            entities.add(cast(entity));

            if (entities.size() == BATCH_SIZE) {
                batchInsert(entities);
                entities.clear();
                log.info("Imported: " + (BATCH_SIZE * iteration) + " items");
                iteration++;
            }
        }
        batchInsert(entities);
    }

}
