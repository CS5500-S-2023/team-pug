package edu.northeastern.cs5500.starterbot.game.blackjack;

import lombok.Data;

/**
 * A class representing the result of a single round of Blackjack. Each result includes the user who
 * played the round and the amount of their bet.
 *
 * <p>The result of a round can be a win, loss, or push (tie). The type of result is determined by
 * the subclass of this class that is used.
 */
@Data
public class Result {
    private String discordId;
    private Double bet;

    /**
     * Constructs a new instance of the {@code Result} class with the specified user and bet amount.
     *
     * @param discordId the user who played the round
     * @param bet the amount of the user's bet
     */
    public Result(String discordId, Double bet) {
        this.discordId = discordId;
        this.bet = bet;
    }
}
