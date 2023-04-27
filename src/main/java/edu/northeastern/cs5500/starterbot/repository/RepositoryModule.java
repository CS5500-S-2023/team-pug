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
    // NOTE: You can use the following lines if you'd like to store objects in
    // memory.
    // NOTE: The presence of commented-out code in your project *will* result in a
    // lowered grade.

    /**
     * Provides an instance of a repository for storing player information.
     *
     * @param repository the in-memory repository instance to be used
     * @return a generic repository for player information
     */
    @Provides
    public GenericRepository<Player> providePlayerRepository(
            InMemoryRepository<Player> repository) {
        return repository;
    }

    /**
     * Provides an instance of a repository for storing Blackjack game data.
     *
     * @param repository the in-memory repository instance to be used
     * @return a generic repository for Blackjack game data
     */
    @Provides
    public GenericRepository<BlackjackGame> provideBlackjackRepository(
            InMemoryRepository<BlackjackGame> repository) {
        return repository;
    }

    /**
     * Provides an instance of a repository for storing slot machine game data.
     *
     * @param repository the in-memory repository instance to be used
     * @return a generic repository for slot machine game data
     */
    @Provides
    public GenericRepository<SlotMachineGame> provideSlotMachineRepository(
            InMemoryRepository<SlotMachineGame> repository) {
        return repository;
    }
    // @Provides
    // public GenericRepository<UserPreference> provideUserPreferencesRepository(
    // MongoDBRepository<UserPreference> repository) {
    // return repository;
    // }

    // @Provides
    // public Class<UserPreference> provideUserPreference() {
    // return UserPreference.class;
    // }

    // @Provides
    // public GenericRepository<Player>
    // providePlayerRepository(MongoDBRepository<Player> repository) {
    // return repository;
    // }

    // @Provides
    // public Class<Player> providePlayer() {
    // return Player.class;
    // }
}
