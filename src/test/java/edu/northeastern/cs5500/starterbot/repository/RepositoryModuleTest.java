package edu.northeastern.cs5500.starterbot.repository;
import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.model.UserPreference;
import org.junit.Test;

public class RepositoryModuleTest {
    RepositoryModule repositoryModule = new RepositoryModule();
    @Test
    public void test(){
        GenericRepository<Player> playerGenericRepository = repositoryModule.providePlayerRepository(new InMemoryRepository<Player>());
        GenericRepository<Player> playerGenericRepository1 = new InMemoryRepository<Player>();
        assertThat(playerGenericRepository).isEqualTo(playerGenericRepository1);

    }

    @Test
    public void test1(){
        GenericRepository<UserPreference> playerGenericRepository = repositoryModule.provideUserPreferencesRepository(new InMemoryRepository<UserPreference>());
        GenericRepository<UserPreference> playerGenericRepository1 = new InMemoryRepository<UserPreference>();
        assertThat(playerGenericRepository).isEqualTo(playerGenericRepository1);

    }

    @Test
    public void test2(){
        GenericRepository<BlackjackGame> playerGenericRepository = repositoryModule.provideBlackjackRepository(new InMemoryRepository<BlackjackGame>());
        GenericRepository<BlackjackGame> playerGenericRepository1 = new InMemoryRepository<BlackjackGame>();
        assertThat(playerGenericRepository).isEqualTo(playerGenericRepository1);

    }

    @Test
    public void test3(){
        GenericRepository<SlotMachineGame> playerGenericRepository = repositoryModule.provideSlotMachineRepository(new InMemoryRepository<SlotMachineGame>());
        GenericRepository<SlotMachineGame> playerGenericRepository1 = new InMemoryRepository<SlotMachineGame>();
        assertThat(playerGenericRepository).isEqualTo(playerGenericRepository1);

    }
}
