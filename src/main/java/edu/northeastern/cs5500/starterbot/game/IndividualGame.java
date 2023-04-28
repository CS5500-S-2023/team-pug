package edu.northeastern.cs5500.starterbot.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IndividualGame class represents the base class for games with individual players. This class
 * should be extended by specific game classes that support individual play.
 *
 * @param <T> The type of individual player participating in the game.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class IndividualGame<T extends IPlayer> extends Game<T> {
    public IndividualGame() {}

    T holder;

    /**
     * Constructor for the IndividualGame class that initializes the game with a config and a player
     * holder.
     *
     * @param config The configuration settings for the game.
     * @param holder The player holder for the game.
     */
    protected IndividualGame(Config config, T holder) {
        super(config);
        this.holder = holder;
    }

    /**
     * Checks if the game can start.
     *
     * @return true if the game can start, false otherwise.
     */
    public boolean canStart() {
        return true;
    }

    /**
     * Returns the current player participating in the game.
     *
     * @return The current player.
     */
    public T getCurrentPlayer() {
        return this.getHolder();
    }
}
