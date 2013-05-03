import play.Application;
import play.GlobalSettings;
import plugins.MongoLinkManager;

public class Global extends GlobalSettings {


    @Override
    public void onStart(Application app) {
        MongoLinkManager.start();
    }


    @Override
    public void onStop(Application app) {
        MongoLinkManager.stop();
    }

}