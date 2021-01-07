package ua.catalog.loader.classes;

public interface Importer<F, T> extends Runnable {
    T cast(F dto);
}
