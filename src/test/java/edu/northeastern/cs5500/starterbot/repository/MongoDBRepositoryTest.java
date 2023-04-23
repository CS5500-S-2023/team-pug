package edu.northeastern.cs5500.starterbot.repository;
import static com.google.common.truth.Truth.assertThat;

//import com.mongodb.MongoClient;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class MongoDBRepositoryTest {
    private static final String DATABASE_NAME = "test_database";
    private static final String COLLECTION_NAME = "test_collection";

    private MongoDBRepository<Player> repository;
    private MongoClient mongoClient;

    @Before
    public void setUp() {
        mongoClient = MongoClients.create();
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Player> collection = mongoDatabase.getCollection(COLLECTION_NAME, Player.class);
        collection.drop(); // make sure collection is empty before each test
        repository = new MongoDBRepository<>(Player.class, new MongoDBService());
    }

    @After
    public void tearDown() {
        mongoClient.close();
    }

    @Test
    public void testAddAndGet() {
        Player player = new Player("test_discord_user_id");
        repository.add(player);
        Player retrievedPlayer = repository.get(player.getId());
        assertThat(player).isEqualTo(retrievedPlayer);
//        assertEquals(player, retrievedPlayer);
    }

    @Test
    public void testUpdate() {
        Player player = new Player("test_discord_user_id");
        repository.add(player);
        player.setUserName("TestUser");
        repository.update(player);
        Player updatedPlayer = repository.get(player.getId());
//        assertEquals("TestUser", updatedPlayer.getUserName());
        assertThat("TestUser").isEqualTo(updatedPlayer.getUserName());
    }

    @Test
    public void testDelete() {
        Player player = new Player("test_discord_user_id");
        repository.add(player);
        repository.delete(player.getId());
//        assertNull(repository.get(player.getId()));
        assertThat(repository.get(player.getId())).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        Player player1 = new Player("test_discord_user_id1");
        Player player2 = new Player("test_discord_user_id2");
        repository.add(player1);
        repository.add(player2);
        Collection<Player> players = repository.getAll();
//        assertEquals(2, players.size());
//        assertTrue(players.contains(player1));
//        assertTrue(players.contains(player2));
        assertThat(2).isEqualTo(players.size());
        assertThat(players.contains(player2)).isEqualTo(true);
        assertThat(players.contains(player1)).isEqualTo(true);
    }

    @Test
    public void testCount() {
        Player player1 = new Player("test_discord_user_id1");
        Player player2 = new Player("test_discord_user_id2");
        repository.add(player1);
        repository.add(player2);
        long count = repository.count();
//        assertEquals(2, count);
        assertThat(2).isEqualTo(count);
    }

    @Test
    public void testContains() {
        Player player = new Player("test_discord_user_id");
        repository.add(player);
//        assertTrue(repository.contains(player.getId()));
        assertThat(repository.contains(player.getId())).isEqualTo(true);
    }
}
