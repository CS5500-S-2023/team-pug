package edu.northeastern.cs5500.starterbot.game.slotMachine;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import lombok.Data;
import net.dv8tion.jda.api.entities.User;

/** SlotMachinePlayer represents a player participating in a slot machine game. */
@Data
public class SlotMachinePlayer implements IPlayer {
    private User user;
    private double bet;
    /**
     * Constructs a new SlotMachinePlayer with the specified user.
     *
     * @param user the user participating in the game
     */
    public SlotMachinePlayer(User user) {
        this.user = user;
    }

    /**
     * Determines if the player can play the game.
     *
     * @return true, as the player can always play the game
     */
    @Override
    public boolean canPlay() {
        return true;
    }
    /**
     * Determines if the player wants to play the game.
     *
     * @return true, as the player always wants to play the game
     */
    @Override
    public boolean wantPlay() {
        return true;
    }
}
