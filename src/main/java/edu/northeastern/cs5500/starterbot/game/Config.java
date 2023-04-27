package edu.northeastern.cs5500.starterbot.game;

import lombok.Data;

/** Config class represents the configuration settings for a game. */
@Data
public class Config {
    private String name;
    private int minPlayers;
    private int maxPlayers;
    /**
     * Constructor for Config that initializes the game with a name and specified minimum and
     * maximum number of players.
     *
     * @param name The name of the game.
     * @param minPlayers The minimum number of players required for the game.
     * @param maxPlayers The maximum number of players allowed in the game.
     */
    public Config(String name, Integer minPlayers, Integer maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }
    /**
     * Constructor for Config that initializes the game with a name and sets the minimum and maximum
     * number of players to 1.
     *
     * @param name The name of the game.
     */
    public Config(String name) {
        this.name = name;
        minPlayers = 1;
        maxPlayers = 1;
    }
}
