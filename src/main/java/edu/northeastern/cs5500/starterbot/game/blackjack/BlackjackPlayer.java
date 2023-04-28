package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import lombok.Data;

/**
 * An abstract class representing a player in a Blackjack game. This class implements the {@link
 * IPlayer} interface and provides basic functionality for a player, such as adding cards to their
 * hand, clearing their hand, and stopping or continuing to play.
 */
@Data
public class BlackjackPlayer implements IPlayer {
    private Hand hand;
    private String discordId;
    private double bet;
    private boolean stop;

    public BlackjackPlayer() {}

    /**
     * Constructs a new instance of the {@code BlackjackPlayer} class with the specified user.
     *
     * @param discordId the Discord user associated with this player
     */
    public BlackjackPlayer(String discordId) {
        this.discordId = discordId;
        hand = new Hand();
        bet = 50;
        stop = false;
    }

    /**
     * Constructs a new instance of the {@code BlackjackPlayer} class with the specified user and
     * bet.
     *
     * @param discordId the Discord user associated with this player
     * @param bet the bet amount for this player
     */
    protected BlackjackPlayer(String discordId, double bet) {
        this.discordId = discordId;
        hand = new Hand();
        this.bet = bet;
        stop = false;
    }

    /**
     * Adds the specified card to this player's hand.
     *
     * @param card the card to add to this player's hand
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /** Clears all cards from this player's hand. */
    public void clearHand() {
        hand.clearCards();
    }

    /**
     * Determines whether this player has chosen to stop playing.
     *
     * @return true if this player has stopped playing, false otherwise
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * Sets whether this player wants to stop playing.
     *
     * @param stop true if this player wants to stop playing, false otherwise
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

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
