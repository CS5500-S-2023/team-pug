package edu.northeastern.cs5500.starterbot.game.slotMachine;

import static edu.northeastern.cs5500.starterbot.game.slotMachine.Symbols.SYMBOLS;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.IndividualGame;
import java.util.Random;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.internal.utils.tuple.Pair;

/**
 * SlotMachineGame represents a single instance of a slot machine game with a given configuration
 * and player.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SlotMachineGame extends IndividualGame<SlotMachinePlayer> {
    public SlotMachineGame() {}

    /**
     * Constructs a new SlotMachineGame with the specified configuration and player.
     *
     * @param config the game configuration
     * @param player the player participating in the game
     */
    public SlotMachineGame(Config config, SlotMachinePlayer player) {
        super(config, player);
    }

    /**
     * Simulates a play in the slot machine game and returns the resulting symbols and payout.
     *
     * @param event the ButtonInteractionEvent that triggered the play
     * @return a Pair containing the resulting symbols in an array and the payout as a double
     */
    public Pair<String[], Double> play(@Nonnull ButtonInteractionEvent event) {
        String[] reels = {spin(), spin(), spin()};
        double payout = sumPayouts(reels);
        return Pair.of(reels, payout);
    }

    /**
     * Simulates a spin of the slot machine reel and returns the resulting symbol.
     *
     * @return the randomly selected symbol
     */
    private String spin() {
        // Randomly select a symbol from the list of symbols
        String[] symbols = SYMBOLS.keySet().toArray(new String[SYMBOLS.size()]);
        return symbols[new Random().nextInt(symbols.length)];
    }

    /**
     * Calculates the payout based on the symbols in the reels.
     *
     * @param reels an array containing the symbols from the three reels
     * @return the payout as a double
     */
    private double sumPayouts(String[] reels) {
        double bet = getCurrentPlayer().getBet();
        System.out.println(bet);
        // Check if all the reels have the same symbol
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            // Multiply the payout by the player's bet
            return SYMBOLS.get(reels[0]) * bet;
        }
        return -bet; // No winnings if the symbols don't match
    }
}
