package plugins;

import com.google.common.collect.Lists;
import com.mongodb.ServerAddress;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;
import play.Configuration;
import play.Logger;
import play.Play;

import java.net.UnknownHostException;

public class MongoLinkManager {

    private static MongoSessionManager mongoSessionManager = null;
    private static MongoLinkManager instance;

    public static void start() {
        getInstance();
    }

    public static MongoLinkManager getInstance() {
        if (instance == null) {
            Logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Logger.info("Starting MongoDB Manager");
            Logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


            Configuration cfg = Play.application().configuration();
            String host = cfg.getString(MongoLinkManager.CFG.HOST);
            int port = cfg.getInt(MongoLinkManager.CFG.PORT);
            String dbName = cfg.getString(MongoLinkManager.CFG.DATABASE);
            String dbUser = cfg.getString(MongoLinkManager.CFG.USER);
            String dbPasswd = cfg.getString(MongoLinkManager.CFG.PASSWORD);
            String packageToScan = cfg.getString(MongoLinkManager.CFG.PACKAGE);

            if (host == null) {
                throw MongoLinkManager.error("Hostname missing");
            }
            if (port == 0) {
                throw MongoLinkManager.error("Port missing");
            }
            if (dbName == null) {
                throw MongoLinkManager.error("Database name missing");
            }
            if (packageToScan == null) {
                throw MongoLinkManager.error("Package with mapping missing");
            }

            instance = new MongoLinkManager(host, port, dbName, dbUser, dbPasswd, packageToScan);
        }
        return instance;
    }

    private MongoLinkManager(String host, int port, String dbName, String dbUser, String dbPasswd, String packageToScan) {
        ServerAddress serverAddress = getServerAddress(host, port);

        ContextBuilderForPlay builder = new ContextBuilderForPlay(packageToScan);
        Settings settings = Settings.defaultInstance()
                .withDefaultUpdateStrategy(UpdateStrategies.DIFF)
                .withDbName(dbName)
                .withAddresses(Lists.newArrayList(serverAddress));
        if (dbUser != null && dbPasswd != null) {
            settings = settings.withAuthentication(dbUser, dbPasswd);
        }
        mongoSessionManager = MongoSessionManager.create(builder, settings);
    }

    private static ServerAddress getServerAddress(String host, int port) {
        ServerAddress serverAddress = null;
        try {
            serverAddress = new ServerAddress(host, port);
        } catch (UnknownHostException e) {
            throw error("Connexion MongoDB impossible");
        }
        return serverAddress;
    }


    protected static MongoSession getSession() {
        if (mongoSessionManager == null) {
            throw error("Connexion MongoDB non initialisée");
        }
        return mongoSessionManager.createSession();
    }

    public static void stop() {
        Logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Logger.info("Shutdown MongoDB Manager");
        Logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        mongoSessionManager.close();
    }


    public enum CFG {
        ;
        public static final String NAME = "mongolink";
        public static final String HOST = "mongolink.db.host";
        public static final String PORT = "mongolink.db.port";
        public static final String USER = "mongolink.db.user";
        public static final String PASSWORD = "mongolink.db.password";
        public static final String DATABASE = "mongolink.db.db";
        public static final String PACKAGE = "mongolink.package.mapping";
    }

    public static RuntimeException error(String meessage) {
        return Configuration.root().reportError(MongoLinkManager.CFG.NAME, meessage, null);
    }
}
