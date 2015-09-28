package de.ur.mi.kilroy.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import static spark.SparkBase.*;

// Main class.
// Set environment variables.

public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class.getCanonicalName());

    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static void main(String[] args) throws Exception {
//        Setup spark.
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");

//        Set openshift variables when deployed or set local variables when running local.
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        int port = 3306;
        String dbName = "kilroy_db";
        String username = "kilroy";
        String password = "kilroywashere";
        if (host != null) {
            port = Integer.parseInt(System.getenv("OPENSHIFT_MYSQL_DB_PORT"));
            dbName = System.getenv("OPENSHIFT_APP_NAME");
            username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
            password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        } else {
            host = "localhost";
        }

//        Initialize database connection.
        logger.info("Connect database to: jdbc:mysql://" + host + ":" + port + "/" + dbName);
        Sql2o sql2o = new Sql2o("jdbc:mysql://" + host + ":" + port + "/" + dbName,
                username, password);
        logger.info("Connected");

//        Start service.
        new KilroyResource(new KilroyService(sql2o));
    }
}
