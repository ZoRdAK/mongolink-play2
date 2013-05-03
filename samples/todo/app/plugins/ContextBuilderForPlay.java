package plugins;

import org.mongolink.domain.mapper.ContextBuilder;
import play.Play;

public class ContextBuilderForPlay extends ContextBuilder {
    public ContextBuilderForPlay(String packageToScan) {
        super(packageToScan);
    }

    @Override
    protected Class loadClass(String className) throws ClassNotFoundException {
        return Play.application().classloader().loadClass(className);
    }
}
