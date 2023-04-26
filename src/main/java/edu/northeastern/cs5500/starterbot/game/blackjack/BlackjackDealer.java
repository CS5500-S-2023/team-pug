package edu.northeastern.cs5500.starterbot.game.blackjack;

import net.dv8tion.jda.api.entities.User;

public class BlackjackDealer extends BlackjackPlayer {

    public BlackjackDealer(User user) {
        super(user);
    }

    public BlackjackDealer(User user, double bet) {
        super(user, bet);
    }

    @Override
    public boolean canPlay() {
        return getHand().getCurrentValue() <= 17;
    }

    @Override
    public boolean wantPlay() {
        return true;
    }
}
