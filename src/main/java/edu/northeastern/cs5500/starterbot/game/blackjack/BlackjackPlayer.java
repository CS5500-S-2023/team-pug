package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import lombok.Data;
import net.dv8tion.jda.api.entities.User;

@Data
public abstract class BlackjackPlayer implements IPlayer {
    private Hand hand;
    private User user;
    private double bet;
    private boolean stop;

    protected BlackjackPlayer(User user) {
        this.user = user;
        hand = new Hand();
        bet = 0;
        stop = false;
    }

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
