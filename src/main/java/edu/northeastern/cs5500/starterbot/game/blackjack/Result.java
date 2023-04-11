package edu.northeastern.cs5500.starterbot.game.blackjack;

import lombok.Data;
import net.dv8tion.jda.api.entities.User;

@Data
public class Result {
    private User user;
    private Double bet;

    public Result(User user, Double bet) {
        this.user = user;
        this.bet = bet;
    }
}
