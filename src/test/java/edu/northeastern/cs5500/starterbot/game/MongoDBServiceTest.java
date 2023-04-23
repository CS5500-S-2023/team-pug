package edu.northeastern.cs5500.starterbot.game;

import com.mongodb.client.MongoDatabase;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MongoDBServiceTest {

    private static MongoDBService mongoDBService;

    @BeforeAll
    public static void setUp() {
        mongoDBService = new MongoDBService();
    }

   
    @Test
    public void testMongoDatabase() {
        MongoDatabase mongoDatabase = mongoDBService.getMongoDatabase();
        Assertions.assertNotNull(mongoDatabase);
    }

}

