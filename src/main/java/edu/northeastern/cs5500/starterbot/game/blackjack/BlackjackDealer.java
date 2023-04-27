package edu.northeastern.cs5500.starterbot.game.blackjack;

import net.dv8tion.jda.api.entities.User;

/**
 * BlackjackDealer class represents a dealer in the game of Blackjack. It extends the
 * BlackjackPlayer class with specific behaviors for a dealer.
 */
public class BlackjackDealer extends BlackjackPlayer {
    /**
     * Constructs a BlackjackDealer instance with the specified User.
     *
     * @param user A User object representing the dealer in the game.
     */
    public BlackjackDealer(User user) {
        super(user);
    }

    /**
     * Constructs a BlackjackDealer instance with the specified User and bet.
     *
     * @param user A User object representing the dealer in the game.
     * @param bet A double value representing the initial bet amount.
     */
    public BlackjackDealer(User user, double bet) {
        super(user, bet);
    }

    /**
     * Checks if the dealer can play based on their current hand value.
     *
     * @return A boolean value. True if the dealer's hand value is less than or equal to 17, false
     *     otherwise.
     */
    @Override
    public boolean canPlay() {
        return getHand().getCurrentValue() <= 17;
    }
    /**
     * Indicates whether the dealer wants to play.
     *
     * @return A boolean value. Always returns true for the dealer.
     */
    @Override
    public boolean wantPlay() {
        return true;
    }
}
