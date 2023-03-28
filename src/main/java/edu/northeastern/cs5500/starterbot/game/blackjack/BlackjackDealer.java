package edu.northeastern.cs5500.starterbot.game.blackjack;

public class BlackjackDealer extends BlackjackPlayer {

    @Override
    public Boolean canPlay() {
        return hand.getCurrentValue() <= 17;
    }
}
