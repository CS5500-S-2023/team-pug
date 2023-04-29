package edu.northeastern.cs5500.starterbot.repository;

import dagger.Module;
import dagger.Provides;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.model.Player;

/**
 * A Dagger module for providing instances of repository classes used in the casino game.
 *
 * <p>This module provides instances of repositories for storing user preferences, player
 * information, and game data for both Blackjack and slot machines.
 *
 * <p>The repository instances are provided through Dagger's dependency injection framework.
 *
 * <p>This module includes commented-out code for using MongoDB repositories instead of in-memory
 * repositories. If you choose to use MongoDB, uncomment the appropriate code and ensure that you
 * have set up a connection to your MongoDB database.
 */
@Module
public class RepositoryModule {

    @Provides
    public GenericRepository<Player> providePlayerRepository(MongoDBRepository<Player> repository) {
        return repository;
    }

    @Provides
    public Class<Player> providePlayer() {
        return Player.class;
    }

    @Provides
    public GenericRepository<SlotMachineGame> provideSlotMachineRepository(
            MongoDBRepository<SlotMachineGame> repository) {
        return repository;
    }

    @Provides
    public Class<SlotMachineGame> provideSlotMachine() {
        return SlotMachineGame.class;
    }

    @Provides
    public GenericRepository<BlackjackGame> provideBlackjackRepository(
            MongoDBRepository<BlackjackGame> repository) {
        return repository;
    }

    @Provides
    public Class<BlackjackGame> provideBlackjack() {
        return BlackjackGame.class;
    }
}
