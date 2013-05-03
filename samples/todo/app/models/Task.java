package models;

import org.bson.types.ObjectId;
import play.data.validation.Constraints;
import plugins.MongoRepository;

public class Task {

    public ObjectId id;

    @Constraints.Required
    public String label;

    public static final MongoRepository<Task> Finder = new MongoRepository<Task>(Task.class);

    public String getIdAsString() {
        return id != null ? id.toString() : "#";
    }
}