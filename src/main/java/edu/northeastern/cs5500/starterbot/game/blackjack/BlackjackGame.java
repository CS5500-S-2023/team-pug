package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.Game;
import lombok.Data;

@Data
public class BlackjackGame extends Game<BlackjackPlayer> {
    private Deck deck;
    private BlackjackDealer dealer;

    public BlackjackGame(Config config, BlackjackPlayer holder) {
        super(config, holder);
        this.deck = new Deck();
        this.dealer = new BlackjackDealer(holder.getUser());
    }

    public void initPlayerCard() {
        int initialCardSize = 2;
        for (BlackjackPlayer blackjackPlayer : players) {
            for (int k = 0; k < initialCardSize; k++) {
                Card card = deck.shuffleDeal();
                blackjackPlayer.addCard(card);
            }
        }
    }

    public void hit() {
        Card card = deck.shuffleDeal();
        getCurrentPlayer().addCard(card);
    }

    public void stand() {
        getCurrentPlayer().setStop(true);
    }

    public void shareDealerBets() {
        // bets from the deal will be shared equally
        int count = 0;
        for (BlackjackPlayer player : players) {

            if (player.canPlay()) count++;
        }
        double sharedBets = dealer.getBet() / count;
        for (BlackjackPlayer player : players) {
            // TODO player.update_balance(shardBets)
        }
    }

    public void shareAllBets() {
        double allbets = players.stream().mapToDouble(i -> i.getBet()).sum();
        int maxValue = 0;
        for (BlackjackPlayer player : players) {
            if (!player.getHand().isBust())
                maxValue = Math.max(maxValue, player.getHand().getCurrentValue());
        }
        int count = 0;
        for (BlackjackPlayer player : players) {
            if (player.getHand().getCurrentValue() != maxValue) {
                // lose its bet
            } else count++;
        }
        double sharedBets = allbets / count;
        for (BlackjackPlayer player : players) {
            if (player.getHand().getCurrentValue() == maxValue) {
                // TODO add sharedBets
            }
        }
    }

    // remove all the player to end the game.
    public void removeAllplayers() {

        for (BlackjackPlayer player : players) {
            removePlayer(player);
        }
    }
}
