package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.model.Model;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Game class represents the base class for a game with a player of type T. This class should be
 * extended by specific game classes.
 *
 * @param <T> The type of player participating in the game.
 */
@Data
public abstract class Game<T extends IPlayer> implements Model {
    ObjectId id;
    protected Config config;

    /** Default constructor for the Game class. */
    public Game() {}

    /**
     * Constructor for the Game class that initializes the game with a config and a player holder.
     *
     * @param config The configuration settings for the game.
     * @param holder The player holder for the game.
     */
    protected Game(Config config) {
        this.config = config;
    }

    /**
     * Hook method to set up any required game state before the game starts. Can be overridden by
     * specific game classes if needed.
     */
    // hook, used when need setup
    public void setup() {}
}
