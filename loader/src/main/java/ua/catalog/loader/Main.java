package ua.catalog.loader;

import org.apache.commons.cli.*;
import ua.catalog.loader.command.ImageCommand;
import ua.catalog.loader.command.ImportCommand;
import ua.catalog.loader.command.PriceCacheCommand;
import ua.catalog.loader.command.SearchCommand;
import ua.catalog.loader.core.config.Config;
import ua.catalog.loader.core.db.Db;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ParseException {


        Config config = Config.getInstance();
        Db.connect(config.getConfiguration().getDb());

        Options options = new Options();
        options.addOption("h", "help", false, "Output help");
        options.addOption("c", "command", true, "Commands: import, price, image, search");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);


        if (cmd.hasOption("h")) {
            printHelp(
                    options,
                    80, // ширина строки вывода
                    "Options:", // строка предшествующая выводу
                    "", // строка следующая за выводом
                    3, // число пробелов перед выводом опции
                    5, // число пробелов перед выводом опцисания опции
                    true, // выводить ли в строке usage список команд
                    System.out // куда производить вывод
            );
        }

        (new PriceCacheCommand()).run();
       // (new SearchCommand()).run();

        if (cmd.hasOption("c")) {

            switch (cmd.getOptionValue("c")) {
                case "import" -> (new ImportCommand()).run();
                case "price" -> (new PriceCacheCommand()).run();
                case "image" -> (new ImageCommand()).run();
                case "search" -> (new SearchCommand()).run();
            }

        }

    }

    public static void printHelp(
            final Options options,
            final int printedRowWidth,
            final String header,
            final String footer,
            final int spacesBeforeOption,
            final int spacesBeforeOptionDescription,
            final boolean displayUsage,
            final OutputStream out) {
        final String commandLineSyntax = "java <loader name>.jar";
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(
                writer,
                printedRowWidth,
                commandLineSyntax,
                header,
                options,
                spacesBeforeOption,
                spacesBeforeOptionDescription,
                footer,
                displayUsage);
        writer.flush();
    }

}
