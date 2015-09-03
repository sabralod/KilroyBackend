package de.ur.mi.kilroy.backend;

import com.beust.jcommander.JCommander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import static spark.SparkBase.*;

/**
 * Created by simon on 02/09/15.
 */
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class.getCanonicalName());

    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_IP") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_IP")) : 8080;

    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");

        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int port = 3306;
        String dbname = "kilroy_db";
        String username = "kilroy";
        String password = "kilroywashere";
        if (host != null) {
            port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
            dbname = System.getenv("OPENSHIFT_APP_NAME");
            username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
            password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        } else {
            host = "localhost";
        }

        Sql2o sql2o = new Sql2o("jdbc:mysql://" + host + ":" + port + "/" + dbname,
                username, password);

        new KilroyResource(new KilroyService(sql2o));
    }
}
