package ua.catalog.loader.config;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ApplicationConfig {

    @Getter
    private Db db;

    public static class Db {
        @Getter
        private String url;
        @Getter
        private String user;
        @Getter
        private String password;

    }

}
