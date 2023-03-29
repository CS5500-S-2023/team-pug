package edu.northeastern.cs5500.starterbot.game.blackjack;

import javax.inject.Inject;

public class BlackjackDealer extends BlackjackPlayer {
    @Inject
    public BlackjackDealer(Hand hand) {
        super(hand);
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
