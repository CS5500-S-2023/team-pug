package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.MuiltiplePlayerGame;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BlackjackGame class represents a game of Blackjack. It extends the MuiltiplePlayerGame abstract
 * class with specific game logic for Blackjack.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlackjackGame extends MuiltiplePlayerGame<BlackjackPlayer> {

    private Deck deck;
    private BlackjackPlayer dealer;

    public BlackjackGame() {}

    /**
     * Constructs a BlackjackGame instance with the specified Config and holder.
     *
     * @param config A Config object representing the game configuration.
     * @param holder A BlackjackPlayer object representing the game holder.
     */
    public BlackjackGame(Config config, BlackjackPlayer holder) {
        super(config, holder);
        this.deck = new Deck();
        this.dealer = new BlackjackPlayer(holder.getDiscordId(), holder.getBet());
    }

    /** Initializes the players' cards by dealing two cards to each player. */
    public void initPlayerCard() {
        int initialCardSize = 2;
        for (BlackjackPlayer blackjackPlayer : players) {
            for (int k = 0; k < initialCardSize; k++) {
                Card card = deck.shuffleDeal();
                blackjackPlayer.addCard(card);
            }
        }
    }

    /**
     * Checks if the game contains the specified discord ID.
     *
     * @param discordId A String representing the discord ID to check.
     * @return A boolean value. True if the game contains the specified discord ID, false otherwise.
     */
    public boolean containsId(String discordId) {
        for (BlackjackPlayer player : players) {
            if (player.getDiscordId().equals(discordId)) {
                return true;
            }
        }
        return false;
    }

    /** Adds a card to the current player's hand. */
    public void hit() {
        Card card = deck.shuffleDeal();
        players.get(currentIndex).addCard(card);
    }

    /** Sets the current player's status to stop. */
    public void stand() {
        players.get(currentIndex).setStop(true);
    }

    /** surrender game */
    public void surrender() {
        double bet = players.get(currentIndex).getBet() / 2;
        players.get(currentIndex).setBet(bet);
        while (!players.get(currentIndex).getHand().isBust())
            players.get(currentIndex).addCard(new Card(Rank.KING, Suit.CLUBS));
    }

    /**
     * double down the bets
     *
     * @return bet amount
     */
    public double doubledown() {
        double bet = players.get(currentIndex).getBet() * 2;
        players.get(currentIndex).setBet(bet);
        return bet;
    }

    /**
     * Distributes the bets among the winners and calculates the results.
     *
     * @return A List of Result objects representing the game results.
     */
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
                if (winnerTotalBets != 0.0) {
                    gameResults.add(
                            new Result(
                                    player.getDiscordId(),
                                    player.getBet()
                                            + player.getBet() / winnerTotalBets * sharedTotalBets));
                }
            } else {
                gameResults.add(new Result(player.getDiscordId(), -player.getBet()));
            }
        }
        return gameResults;
    }
}
