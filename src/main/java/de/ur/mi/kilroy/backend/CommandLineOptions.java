package de.ur.mi.kilroy.backend;

import com.beust.jcommander.Parameter;

/**
 * Created by simon on 02/09/15.
 */
public class CommandLineOptions {
    @Parameter(names = "--debug")
    public boolean debug = false;

    @Parameter(names = {"--database"})
    public String database = "kilroy_db";

    @Parameter(names = {"--db-host"})
    public String dbHost = "localhost";

    @Parameter(names = {"--db-username"})
    public String dbUsername = "kilroy";

    @Parameter(names = {"--db-password"})
    public String dbPassword = "kilroywashere";

    @Parameter(names = {"--db-port"})
    public Integer dbPort = 3306;
}
