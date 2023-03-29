package edu.northeastern.cs5500.starterbot.game.blackjack;

import javax.inject.Inject;

public class BlackjackNormalPlayer extends BlackjackPlayer {
    @Inject
    public BlackjackNormalPlayer(Hand hand) {
        super(hand);
    }

    @Override
    public boolean canPlay() {
        return !getHand().isBust();
    }

    @Override
    public boolean wantPlay() {
        return isStop();
    }
}
