package edu.northeastern.cs5500.starterbot.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * MongoDBService is a singleton class responsible for establishing a connection to the MongoDB
 * database and providing access to its instance.
 */
@Singleton
@Slf4j
public class MongoDBService implements Service {
    /**
     * Retrieves the MongoDB connection URI from the environment variable or returns a default
     * connection string for local development.
     *
     * @return A String representing the MongoDB connection URI.
     */
    static String getDatabaseURI() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        final String databaseURI = processBuilder.environment().get("MONGODB_URI");
        if (databaseURI != null) {
            return databaseURI;
        }
        return "mongodb://localhost:27017/Stuff"; // connect to localhost by default
    }
    /** The instance of the connected MongoDatabase. */
    @Getter private MongoDatabase mongoDatabase;
    /**
     * Constructs a MongoDBService instance and connects to the MongoDB database using the
     * connection URI obtained from the environment variable or a default value.
     */
    @Inject
    public MongoDBService() {
        CodecRegistry codecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        ConnectionString connectionString = new ConnectionString(getDatabaseURI());

        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .codecRegistry(codecRegistry)
                        .applyConnectionString(connectionString)
                        .build();

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        mongoDatabase = mongoClient.getDatabase(connectionString.getDatabase());
    }
    /** Registers the MongoDBService instance. */
    @Override
    public void register() {
        log.info("MongoDBService > register");
    }
}
