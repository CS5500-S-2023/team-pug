package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.model.Model;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public abstract class Game<T extends IPlayer> implements Model {
    ObjectId id;
    protected Config config;
    protected T holder;

    protected Game() {}

    protected Game(Config config, T holder) {
        this.config = config;
        this.holder = holder;
    }

    // hook, used when need setup
    public void setup() {}
}
