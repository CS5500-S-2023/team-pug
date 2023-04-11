package edu.northeastern.cs5500.starterbot.model;

import edu.northeastern.cs5500.starterbot.game.Game;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Data;

@Singleton
@Data
public class Casino {
    private List<Game> games;

    @Inject
    public Casino() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }
}
