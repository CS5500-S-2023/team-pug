package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.Data;

@Data
public abstract class MuiltiplePlayerGame<T extends IPlayer> extends Game<T> {
    @Inject PlayerController playerController;
    protected int currentIndex;
    protected List<T> players;

    protected MuiltiplePlayerGame(Config config, T holder) {
        super(config, holder);
        players = new ArrayList<>();
        joinPlayer(holder);
        currentIndex = 0;
    }

    public boolean canStart() {
        if (players.size() < config.getMinPlayers()) return false;
        else return true;
    }

    public boolean canJoin(String discordId) {
        if (players.size() >= config.getMaxPlayers()) return false;
        else return true;
    }

    public void joinPlayer(T player) {
        players.add(player);
    }

    public void removePlayer(T t) {
        players.remove(t);
    }

    public boolean isEndOfRound() {
        int tempIndex = currentIndex + 1;
        while (tempIndex < players.size()
                && (!players.get(tempIndex).canPlay() || !players.get(currentIndex).wantPlay())) {
            tempIndex++;
        }
        return tempIndex == players.size();
    }

    public T nextPlayer() {
        currentIndex = (currentIndex + 1) % players.size();
        while (!players.get(currentIndex).canPlay() || !players.get(currentIndex).wantPlay())
            currentIndex = (currentIndex + 1) % players.size();
        return players.get(currentIndex);
    }

    public T getCurrentPlayer() {
        return players.get(currentIndex);
    }
}
