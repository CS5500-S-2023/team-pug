package edu.northeastern.cs5500.starterbot.game.blackjack;

/**
 * A class representing a normal player in a Blackjack game. This class extends the {@link
 * BlackjackPlayer} class.
 */
public class BlackjackNormalPlayer extends BlackjackPlayer {
    public BlackjackNormalPlayer() {
        super();
    }

    /**
     * Constructs a new instance of the {@code BlackjackNormalPlayer} class with the specified user.
     *
     * @param discordId the Discord user associated with this player
     */
    public BlackjackNormalPlayer(String discordId) {
        super(discordId);
    }

    /**
     * Constructs a new instance of the {@code BlackjackNormalPlayer} class with the specified user
     * and bet.
     *
     * @param discordId the Discord user associated with this player
     * @param bet the bet amount for this player
     */
    public BlackjackNormalPlayer(String discordId, double bet) {
        super(discordId, bet);
    }

    /**
     * Determines whether this player can play their turn. This is based on whether the player's
     * hand has exceeded a score of 21.
     *
     * @return true if this player can play, false otherwise
     */
    @Override
    public boolean canPlay() {
        return !getHand().isBust();
    }

    /**
     * Determines whether this player wants to continue playing their turn. This is based on whether
     * the player has chosen to "stop" or not.
     *
     * @return true if this player wants to play, false otherwise
     */
    @Override
    public boolean wantPlay() {
        return !isStop();
    }
}
