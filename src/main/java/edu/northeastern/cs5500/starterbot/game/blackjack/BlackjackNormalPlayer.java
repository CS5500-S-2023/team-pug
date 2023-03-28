package edu.northeastern.cs5500.starterbot.game.blackjack;

public class BlackjackNormalPlayer extends BlackjackPlayer {

    @Override
    public Boolean canPlay() {
        // TODO Auto-generated method stub
        return !hand.isBust();
    }
}
