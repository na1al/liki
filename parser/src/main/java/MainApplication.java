
import command.ImportCommand;
import command.PriceCacheCommand;
import command.SearchCommand;
import core.config.Config;
import core.db.Db;

import java.io.IOException;
import java.sql.*;

public class MainApplication {

    public static void main(String[] args) throws IOException, SQLException {

        Config config = Config.getInstance();
        Db.connect(config.getConfiguration().getDb());

//        (new ImportCommand()).run();
//        (new PriceCacheCommand()).run();
//        (new ImageCommand()).run();
        (new SearchCommand()).run();
    }

}
