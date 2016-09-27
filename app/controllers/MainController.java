package controllers;

import com.google.gson.Gson;
import mongo.Communicator;
import play.mvc.*;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * A Java Play controller.
 * @author Florian
 */
public class MainController extends Controller {

    /**
     * Java Injection of the communicator.
     * With Play 2.5.x, it's recommended to use Java Injection.
     * @see Communicator
     */
    @Inject
    private Communicator communicator;

    /**
     * Asynchronous controller call that returns a JSON containing every document of a collection.
     * This method is called by /collection/:name (see routes file).
     * @param collection The name of the collection
     * @return The list of every document inside the collection serialized in JSON.
     */
    public CompletionStage<Result> getCollection(String collection) {
        /**
         * Use the future response of the communicator and return ok with the list in JSON
         * once the communicator query is over.
         */
        return communicator.getCollection(collection)
                .thenApply(
                        list -> ok(new Gson().toJson(list))
                );
    }
}
