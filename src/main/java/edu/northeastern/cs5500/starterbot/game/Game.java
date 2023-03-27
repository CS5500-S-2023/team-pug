package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.model.Casino;
import edu.northeastern.cs5500.starterbot.model.Player;
import java.util.List;
import java.util.Queue;
import lombok.Data;

@Data
public abstract class Game {
    String gameName;
    int minPlayers;
    int maxPlayers;
    Casino casino;
    List<Player> players;
    Queue<Player> waitList;

    final void play() {
        startGame();
        while (true) {
            loadWaitlist();
            if (players.isEmpty()) break;
            if (players.size() >= minPlayers) {
                // start play
                move();
                makeBet(players);
                handleResult(players);
            }
        }
        endGame();
    }

    Game(String gameName, Casino casino, Player player) {
        this.gameName = gameName;
        minPlayers = 1;
        maxPlayers = 1;
        this.casino = casino;
        addPlayer(player);
    }

    Game(String gameName, int minPlayers, int maxPlayers, Casino casino, Player player) {
        this.gameName = gameName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.casino = casino;
        addPlayer(player);
    }

    // generate context of the game
    abstract void move();

    // players make bets
    abstract void makeBet(List<Player> players);

    // game results handle
    abstract void handleResult(List<Player> players);

    private void loadWaitlist() {
        while (players.size() < maxPlayers && !waitList.isEmpty()) {
            players.add(waitList.poll());
        }
    }

    private void startGame() {
        casino.addGame(this);
    }

    private void endGame() {
        casino.removeGame(this);
    }

    public void addPlayer(Player player) {
        waitList.add(player);
    }
    ;

    public void removePlayer(Player player) {
        players.remove(player);
    }
    ;
}
