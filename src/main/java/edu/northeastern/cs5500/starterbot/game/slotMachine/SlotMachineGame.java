package edu.northeastern.cs5500.starterbot.game.slotMachine;

import static edu.northeastern.cs5500.starterbot.game.slotMachine.Symbols.SYMBOLS;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.IndividualGame;
import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import java.util.Random;
import javax.annotation.Nonnull;
import lombok.Data;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
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
        // Calculate the total payout based on the combination of symbols
        int payout = 0;
        for (String symbol : reels) {
            payout += SYMBOLS.get(symbol);
        }
        return payout;
    }
}
