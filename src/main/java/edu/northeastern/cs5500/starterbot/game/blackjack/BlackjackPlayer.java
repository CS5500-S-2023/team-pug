package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.model.Player;

public abstract class BlackjackPlayer extends Player {
    Hand hand;
    double bet;

    public void clearHand() {
        hand.clearCards();
    }
    
    public abstract Boolean canPlay();
}
