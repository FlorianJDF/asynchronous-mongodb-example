package mongo;


import org.bson.Document;
import play.Configuration;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * A Java class that handle Mongo queries
 * @author Florian
 */
public class Communicator {

    private Configuration configuration;

    /**
     * Communicator construction.
     * With Java Injection in order to access the Play configuration file.
     * See Play documentation for more detail about Java Injection with Play.
     * @param configuration The Play configuration file.
     */
    @Inject
    public Communicator(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * Asynchronous method that execute a find query to the Mongo database,
     * in order to get every document of a collection.
     * @param collection Name of the collection.
     * @return A CompletableFuture containing the ArrayList of every document in the collection.
     */
    public CompletableFuture getCollection(String collection){
        ArrayList<Document> collection_elements = new ArrayList<>();
        CompletableFuture mongo_result = new CompletableFuture();
        // Create a Mongo connector with the connection string specified in the configuration file.
        Connector mongo_connector = new Connector(configuration.getString("connectionString"));

        /**
         * Once the connection to the Mongodb is established, we use the method "find" and "forEach"
         * in order to iterate on each document in the collection (see mongodb-driver-async documentation).
         *
         * The "forEach" method takes two arguments :
         * 1 - A lambda expression that add each document in the list,
         * 2 - A lambda expression that complete the future with the list of document,
         *     then close the Mongo connection.
         */
        mongo_connector.connectToMongo()
                .getCollection(collection)
                .find()
                .forEach(
                        document -> collection_elements.add(document),
                        (result, t) -> {
                            mongo_result.complete(collection_elements);
                            mongo_connector.closeConnection();
                        }
                );

        return mongo_result;

    }

}
