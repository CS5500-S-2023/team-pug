package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.model.Model;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public abstract class Game<T extends IPlayer> implements Model {
    ObjectId id;
    protected Config config;
    protected T holder;
    protected List<T> players;
    protected int currentIndex;

    protected Game(Config config, T holder) {
        this.config = config;
        this.holder = holder;
        players = new ArrayList<>();
        joinPlayer(holder);
        currentIndex = 0;
    }

    // hook, used when need setup
    public void setup() {}

    public boolean canStart() {
        if (players.size() < config.getMinPlayers()) return false;
        else return true;
    }

    public boolean canJoin() {
        if (players.size() > config.getMaxPlayers()) return false;
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
    ;

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
