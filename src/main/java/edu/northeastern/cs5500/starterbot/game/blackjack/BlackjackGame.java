package edu.northeastern.cs5500.starterbot.game.blackjack;

import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.game.Game;
import edu.northeastern.cs5500.starterbot.model.Casino;

public class BlackjackGame extends Game<BlackjackPlayer> {
    private static final String GAME_NAME = "Blackjack";
    @Inject
    Deck deck;
    @Inject
    BlackjackDealer dealer;

    @Inject
    public BlackjackGame(int minPlayers, int maxPlayers, Casino casino, BlackjackPlayer player) {
        super(GAME_NAME, minPlayers, maxPlayers, casino, player);
    }

    @Override
    public void playRound() {
        this.deck.shuffle();
        int continuePlayerNumber = 0;
        // player take turn
        for (BlackjackPlayer player : players) {

            if (player.canPlay() && player.wantPlay()) {
                // TODO replace input with message sent from jda
                String input = "";

                if (input.equals("HIT")) {

                    Card card = deck.deal();
                    player.addCard(card);
                    if (!player.getHand().isBust())
                        continuePlayerNumber += 1;

                } else if (input.equals("STAND")) {

                    player.setStop(true);
                }
            }
        }
        // determine if it is the end of the game
        if (dealer.getHand().isBust()) {
            shareDealerBets();
            removeAllplayers();
        } else if (continuePlayerNumber == 0) {
            shareAllBets();
            removeAllplayers();
        }

    }

    private void shareDealerBets() {
        // bets from the deal will be shared equally
        int count = 0;
        for (BlackjackPlayer player : players) {

            if (player.canPlay())
                count++;
        }
        double sharedBets = dealer.getBet() / count;
        for (BlackjackPlayer player : players) {
            // player.update_balance(shardBets)
        }
    }

    private void shareAllBets() {
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
            } else
                count++;
        }
        double sharedBets = allbets / count;
        for (BlackjackPlayer player : players) {
            if (player.getHand().getCurrentValue() == maxValue) {
                // add sharedBets
            }
        }
    }

    // remove all the player to end the game.
    private void removeAllplayers() {

        for (BlackjackPlayer player : players) {
            removePlayer(player);
        }
    }
}
