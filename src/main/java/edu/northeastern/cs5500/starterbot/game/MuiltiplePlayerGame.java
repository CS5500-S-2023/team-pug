package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MuiltiplePlayerGame is an abstract class representing a game with multiple players. It extends
 * the Game class and provides additional functionality to manage multiple players. Classes
 * extending this class should provide specific game logic.
 *
 * @param <T> the type of the player, which must extend the IPlayer interface.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class MuiltiplePlayerGame<T extends IPlayer> extends Game<T> {
    @Inject PlayerController playerController;

    protected int currentIndex;
    protected List<T> players;
    T holder;

    public MuiltiplePlayerGame() {}

    /**
     * Constructs a new MuiltiplePlayerGame with the given configuration and initial player.
     *
     * @param config the game configuration.
     * @param holder the initial player.
     */
    protected MuiltiplePlayerGame(Config config, T holder) {
        super(config);
        this.holder = holder;
        players = new ArrayList<>();
        joinPlayer(holder);
        currentIndex = 0;
    }

    /**
     * Checks if the game can be started based on the minimum number of players.
     *
     * @return true if the game can start, false otherwise.
     */
    public boolean canStart() {
        if (players.size() < config.getMinPlayers()) return false;
        else return true;
    }

    /**
     * Checks if a player can join the game based on the maximum number of players.
     *
     * @return true if the player can join, false otherwise.
     */
    public boolean canJoin() {
        if (players.size() >= config.getMaxPlayers()) return false;
        else return true;
    }

    /**
     * Adds a player to the game.
     *
     * @param player the player to add.
     */
    public void joinPlayer(T player) {
        players.add(player);
    }

    /**
     * Removes a player from the game.
     *
     * @param t the player to remove.
     */
    public void removePlayer(T t) {
        players.remove(t);
    }

    /**
     * Checks if the end of the round has been reached.
     *
     * @return true if the end of the round has been reached, false otherwise.
     */
    public boolean isEndOfRound() {
        int tempIndex = currentIndex + 1;
        while (tempIndex < players.size()
                && (!players.get(tempIndex).canPlay() || !players.get(currentIndex).wantPlay())) {
            tempIndex++;
        }
        return tempIndex == players.size();
    }

    /**
     * Returns the next player in the rotation.
     *
     * @return the next player.
     */
    public T nextPlayer() {
        currentIndex = (currentIndex + 1) % players.size();
        while (!players.get(currentIndex).canPlay() || !players.get(currentIndex).wantPlay())
            currentIndex = (currentIndex + 1) % players.size();
        return players.get(currentIndex);
    }
}
