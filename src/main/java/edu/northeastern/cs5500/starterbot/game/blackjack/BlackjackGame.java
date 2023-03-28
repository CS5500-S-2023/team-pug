package edu.northeastern.cs5500.starterbot.game.blackjack;

import edu.northeastern.cs5500.starterbot.game.Game;
import edu.northeastern.cs5500.starterbot.model.Casino;

public class BlackjackGame extends Game<BlackjackPlayer> {
    private static final String GAME_NAME = "Blackjack";
    Deck deck;

    public BlackjackGame(int minPlayers, int maxPlayers, Casino casino, BlackjackPlayer player) {
        super(GAME_NAME, minPlayers, maxPlayers, casino, player);
        deck = new Deck();
    }

    @Override
    public void playRound() {
        this.deck.shuffle();
        for (BlackjackPlayer player : players) {

        }
    }
}
