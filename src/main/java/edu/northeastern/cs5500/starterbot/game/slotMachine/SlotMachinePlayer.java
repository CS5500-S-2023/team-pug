package edu.northeastern.cs5500.starterbot.game.slotMachine;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import lombok.Data;
import net.dv8tion.jda.api.entities.User;

@Data
public class SlotMachinePlayer implements IPlayer {
    private User user;
    private double bet;

    public SlotMachinePlayer(User user) {
        this.user = user;
    }

    @Override
    public boolean canPlay() {
        return true;
    }

    @Override
    public boolean wantPlay() {
        return true;
    }
}
