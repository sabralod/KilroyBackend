package de.ur.mi.kilroy.backend;

import com.beust.jcommander.JCommander;
import org.sql2o.Sql2o;

import java.util.logging.Logger;

import static spark.SparkBase.*;

/**
 * Created by simon on 02/09/15.
 */
public class Bootstrap {

    private static final Logger logger = Logger.getLogger(Bootstrap.class.getCanonicalName());

    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_IP") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_IP")) : 8080;

    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        logger.finest("Options.debug = " + options.debug);
        logger.finest("Options.database = " + options.database);
        logger.finest("Options.dbHost = " + options.dbHost);
        logger.finest("Options.dbUsername = " + options.dbUsername);
        logger.finest("Options.dbPort = " + options.dbPort);

        Sql2o sql2o = new Sql2o("jdbc:mysql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword);

        new KilroyResource(new KilroyService(sql2o));
    }
}
