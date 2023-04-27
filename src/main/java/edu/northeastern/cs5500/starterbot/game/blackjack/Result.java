package edu.northeastern.cs5500.starterbot.game.blackjack;

import lombok.Data;
import net.dv8tion.jda.api.entities.User;

/**
 * A class representing the result of a single round of Blackjack. Each result includes the user who
 * played the round and the amount of their bet.
 *
 * <p>The result of a round can be a win, loss, or push (tie). The type of result is determined by
 * the subclass of this class that is used.
 */
@Data
public class Result {
    private User user;
    private Double bet;
    /**
     * Constructs a new instance of the {@code Result} class with the specified user and bet amount.
     *
     * @param user the user who played the round
     * @param bet the amount of the user's bet
     */
    public Result(User user, Double bet) {
        this.user = user;
        this.bet = bet;
    }
}
