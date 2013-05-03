package controllers;

import models.Task;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    static Form<Task> taskForm = Form.form(Task.class);

    public static Result index() {
        return redirect(routes.Application.tasks());
    }

    public static Result tasks() {
        return ok(
                views.html.index.render(Task.Finder.all(), taskForm)
        );
    }

    public static Result newTask() {
        Form<Task> filledForm = taskForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(Task.Finder.all(), filledForm)
            );
        } else {
            Task.Finder.add(filledForm.get());
            return redirect(routes.Application.tasks());
        }
    }

    public static Result deleteTask(String id) {
        Task task = Task.Finder.get(new ObjectId(id));
        Task.Finder.delete(task);
        return redirect(routes.Application.tasks());
    }

}
