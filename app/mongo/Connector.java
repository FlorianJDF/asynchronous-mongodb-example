package mongo;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;

/**
 * A Java class that creates and manage a Mongo connector
 * to the given connection string.
 * @author Florian
 */
public class Connector {
    private String mongodbConnectionString;
    private MongoClient mongoClient;

    /**
     * Connector constructor.
     * @param mongodbConnectionString The Mongo Database connection string.
     */
    public Connector(String mongodbConnectionString) {
        this.mongodbConnectionString = mongodbConnectionString;
    }

    /**
     * Establish a connection to the Mongo Database.
     * @return The database connection.
     */
    public MongoDatabase connectToMongo(){
        ConnectionString mongoConnectionString = new ConnectionString(mongodbConnectionString);
        MongoClient mongoClient = MongoClients.create(mongoConnectionString);
        this.mongoClient = mongoClient;
        MongoDatabase db = mongoClient.getDatabase(mongoConnectionString.getDatabase());

        return db;
    }

    /**
     * Close the Mongo connexion established by the Mongo client.
     */
    public void closeConnection(){
        mongoClient.close();
    }

    public String getMongodbConnectionString() {
        return mongodbConnectionString;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
