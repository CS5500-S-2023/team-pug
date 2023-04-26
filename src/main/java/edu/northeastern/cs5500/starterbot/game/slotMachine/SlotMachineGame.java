package edu.northeastern.cs5500.starterbot.game.slotMachine;

import static edu.northeastern.cs5500.starterbot.game.slotMachine.Symbols.SYMBOLS;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.IndividualGame;
import java.util.Random;
import javax.annotation.Nonnull;
import lombok.Data;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.internal.utils.tuple.Pair;

@Data
public class SlotMachineGame extends IndividualGame<SlotMachinePlayer> {

    public SlotMachineGame(Config config, SlotMachinePlayer player) {
        super(config, player);
    }

    public Pair<String[], Double> play(@Nonnull ButtonInteractionEvent event) {
        String[] reels = {spin(), spin(), spin()};
        double payout = sumPayouts(reels);
        return Pair.of(reels, payout);
    }

    private String spin() {
        // Randomly select a symbol from the list of symbols
        String[] symbols = SYMBOLS.keySet().toArray(new String[SYMBOLS.size()]);
        return symbols[new Random().nextInt(symbols.length)];
    }

    private double sumPayouts(String[] reels) {
        double bet = getCurrentPlayer().getBet();
        // Check if all the reels have the same symbol
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            // Multiply the payout by the player's bet
            return SYMBOLS.get(reels[0]) * bet;
        }
        return -bet; // No winnings if the symbols don't match
    }
}
