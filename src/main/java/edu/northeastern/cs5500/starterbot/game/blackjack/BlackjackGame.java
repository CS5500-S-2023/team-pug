package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.MuiltiplePlayerGame;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BlackjackGame extends MuiltiplePlayerGame<BlackjackPlayer> {
    private Deck deck;
    private BlackjackDealer dealer;

    public BlackjackGame(Config config, BlackjackPlayer holder) {
        super(config, holder);
        this.deck = new Deck();
        this.dealer = new BlackjackDealer(holder.getUser(), holder.getBet());
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

    public boolean containsId(String discordId) {
        for (BlackjackPlayer player : players) {
            if (player.getUser().getId().equals(discordId)) {
                return true;
            }
        }
        return false;
    }

    public void hit() {
        Card card = deck.shuffleDeal();
        getCurrentPlayer().addCard(card);
    }

    public void stand() {
        getCurrentPlayer().setStop(true);
    }

    // public List<Result> shareDealerBets() {
    // double sharedTotalBets = dealer.getBet();
    // double winnerTotalBets = 0;
    // for (BlackjackPlayer player : players) {
    // if (player.canPlay()) {
    // winnerTotalBets += player.getBet();
    // } else {
    // sharedTotalBets += player.getBet();
    // }
    // }
    // List<Result> gameResults = new ArrayList<>();
    // for (BlackjackPlayer player : players) {
    // if (player.canPlay()) {
    // gameResults.add(
    // new Result(
    // player.getUser(),
    // player.getBet() / winnerTotalBets * sharedTotalBets));
    // } else {
    // gameResults.add(new Result(player.getUser(), -player.getBet()));
    // }
    // }
    // return gameResults;
    // }

    public List<Result> shareAllBets() {
        double sharedTotalBets = 0;
        double winnerTotalBets = 0;
        int maxValue = 0;
        for (BlackjackPlayer player : players) {
            if (!player.getHand().isBust())
                maxValue = Math.max(maxValue, player.getHand().getCurrentValue());
        }
        for (BlackjackPlayer player : players) {
            if (player.getHand().getCurrentValue() != maxValue) sharedTotalBets += player.getBet();
            else winnerTotalBets += player.getBet();
        }
        List<Result> gameResults = new ArrayList<>();
        for (BlackjackPlayer player : players) {
            if (player.getHand().getCurrentValue() == maxValue) {
                gameResults.add(
                        new Result(
                                player.getUser(),
                                player.getBet() / winnerTotalBets * sharedTotalBets));
            } else {
                gameResults.add(new Result(player.getUser(), -player.getBet()));
            }
        }
        return gameResults;
    }

    // remove all the player to end the game.
    // public void removeAllplayers() {

    // for (BlackjackPlayer player : players) {
    // removePlayer(player);
    // }
    // }
}
