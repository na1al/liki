package ua.catalog.loader.core.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import ua.catalog.loader.config.ApplicationConfig;

import java.io.File;
import java.io.IOException;


public class Config {

    private static Config instance;

    @Getter
    private ApplicationConfig configuration;

    private Config(JsonFactory factory) throws IOException {
        ObjectMapper mapper = new ObjectMapper(factory);
        configuration = mapper.readValue(new File("src/main/resources/application.yaml"), ApplicationConfig.class);
    }

    public static Config getInstance() throws IOException {

        if (instance != null) {
            return instance;
        }

        instance = new Config(new YAMLFactory());

        return instance;

    }

}
