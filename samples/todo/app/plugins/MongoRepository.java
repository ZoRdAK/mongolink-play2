package plugins;

import org.mongolink.MongoSession;

import java.util.List;

public class MongoRepository<T> {

    private final Class<T> clazz;

    public MongoRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T get(Object id) {
        MongoSession session = getStartedSession();
        T resource = session.get(id, clazz);
        session.stop();
        return resource;
    }

    public void delete(T entity) {
        MongoSession session = getStartedSession();
        session.delete(entity);
        session.stop();
    }

    public void add(T entity) {
        MongoSession session = getStartedSession();
        session.save(entity);
        session.stop();
    }


    public List<T> all() {
        MongoSession session = getStartedSession();
        List<T> resources = session.getAll(clazz);
        session.stop();
        return resources;
    }

    private static MongoSession getStartedSession() {
        MongoSession session = MongoLinkManager.getSession();
        session.start();
        return session;
    }

}
