package edu.northeastern.cs5500.starterbot.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class IndividualGame<T extends IPlayer> extends Game<T> {

    protected IndividualGame(Config config, T holder) {
        super(config, holder);
    }

    public boolean canStart() {
        return true;
    }

    public T getCurrentPlayer() {
        return getHolder();
    }
}
