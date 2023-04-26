package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.Player;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class InMemoryRepositoryTest {
    @Test
    void test() {
        //        Injector injector = Guice.createInjector(new Module());
        RepositoryModule repositoryModule = new RepositoryModule();
        GenericRepository<Player> repository =
                repositoryModule.providePlayerRepository(new InMemoryRepository<>());
        //        GenericRepository<Player> repository = injector.getInstance(new
        // Key<GenericRepository<Player>>() {});

        // create a new player
        Player newPlayer = new Player("123456789");
        repository.add(newPlayer);

        // update player information
        Player player = repository.get(newPlayer.getId());
        player.setUserName("Alice");
        player.setBalance(500.00);
        repository.update(player);

        // retrieve all players
        Collection<Player> players = repository.getAll();
        System.out.println("All players:");
        for (Player p : players) {
            System.out.println(
                    p.getUserName() + ", " + p.getBalance() + ", " + p.getLastLoginTime());
        }

        // delete player
        repository.delete(newPlayer.getId());

        // count number of players
        long count = repository.count();
        System.out.println("Number of players: " + count);
    }
}
