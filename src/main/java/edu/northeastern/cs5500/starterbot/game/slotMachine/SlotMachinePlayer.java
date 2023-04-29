package edu.northeastern.cs5500.starterbot.game.slotMachine;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import lombok.Data;

/** SlotMachinePlayer represents a player participating in a slot machine game. */
@Data
public class SlotMachinePlayer implements IPlayer {
    private String discordId;
    private double bet;

    public SlotMachinePlayer() {}

    /**
     * Constructs a new SlotMachinePlayer with the specified user.
     *
     * @param discordId the user participating in the game
     */
    public SlotMachinePlayer(String discordId) {
        this.discordId = discordId;
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
