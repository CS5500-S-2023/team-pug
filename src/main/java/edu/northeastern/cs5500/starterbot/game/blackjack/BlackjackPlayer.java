package edu.northeastern.cs5500.starterbot.game.blackjack;

import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.model.Player;
import lombok.Data;

@Data
public abstract class BlackjackPlayer extends Player {
    private Hand hand;
    private double bet;
    private boolean stop;

    protected BlackjackPlayer(Hand hand) {
        this.hand = hand;
        bet = 0;
        stop = false;
    }

    public abstract boolean canPlay();

    public abstract boolean wantPlay();

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void clearHand() {
        hand.clearCards();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
