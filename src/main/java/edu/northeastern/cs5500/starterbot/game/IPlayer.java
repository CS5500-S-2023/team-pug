package edu.northeastern.cs5500.starterbot.game;
/**
 * IPlayer interface represents a common interface for players participating in games. Classes
 * implementing this interface should provide the specific implementation for the canPlay() and
 * wantPlay() methods.
 */
public interface IPlayer {
    /**
     * Checks if the player is able to play the game.
     *
     * @return true if the player can play, false otherwise.
     */
    public abstract boolean canPlay();
    /**
     * Checks if the player wants to play the game.
     *
     * @return true if the player wants to play, false otherwise.
     */
    public abstract boolean wantPlay();
}
