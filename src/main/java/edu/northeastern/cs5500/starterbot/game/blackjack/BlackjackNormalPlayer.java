package edu.northeastern.cs5500.starterbot.game.blackjack;

import net.dv8tion.jda.api.entities.User;

public class BlackjackNormalPlayer extends BlackjackPlayer {

    public BlackjackNormalPlayer(User user) {
        super(user);
    }

    public BlackjackNormalPlayer(User user, double bet) {
        super(user, bet);
    }

    @Override
    public boolean canPlay() {
        return !getHand().isBust();
    }

    @Override
    public boolean wantPlay() {
        return !isStop();
    }
}
