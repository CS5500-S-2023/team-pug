package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.model.Casino;
import edu.northeastern.cs5500.starterbot.model.Player;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Data;

@Data
public abstract class Game<T extends Player> {
    String gameName;
    protected int minPlayers;
    protected int maxPlayers;
    protected Casino casino;
    protected List<T> players;
    protected Queue<T> waitList;

    public Game(String gameName, int minPlayers, int maxPlayers, Casino casino, T player) {
        this.gameName = gameName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.casino = casino;
        players = new ArrayList<>();
        waitList = new LinkedList<>();
        addPlayer(player);
    }

    Game(String gameName, Casino casino, T player) {
        this.gameName = gameName;
        minPlayers = 1;
        maxPlayers = 1;
        this.casino = casino;
        players = new ArrayList<>();
        waitList = new LinkedList<>();
        addPlayer(player);
    }

    public final void play() {
        startGame();
        setup();
        while (true) {
            loadWaitlist();
            if (players.isEmpty())
                break;
            if (players.size() >= minPlayers) {
                // start play
                playRound();
            }
        }
        endGame();
    }

    // hook, used when need setup
    public void setup() {
    }

    public abstract void playRound();

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

    public void addPlayer(T t) {
        waitList.add(t);
    }

    public void removePlayer(T t) {
        players.remove(t);
    }
}
