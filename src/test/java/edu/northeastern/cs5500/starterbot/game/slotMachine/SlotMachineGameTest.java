package edu.northeastern.cs5500.starterbot.game.slotMachine;

import static edu.northeastern.cs5500.starterbot.game.slotMachine.Symbols.SYMBOLS;
import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.Config;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.internal.entities.UserImpl;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SlotMachineGameTest {

    private SlotMachineGame slotMachineGame;
    private SlotMachinePlayer slotMachinePlayer;

    @BeforeEach
    void setUp() {
        User user = new UserImpl(1234567890L, null);
        slotMachinePlayer = new SlotMachinePlayer(user);
        slotMachinePlayer.setBet(10);
        slotMachineGame =
                new SlotMachineGame(new Config("SLOT_MACHINE_GAME_NAME"), slotMachinePlayer);
    }

    @Test
    void testPlay() {
        for (int i = 0; i < 10; i++) {
            Pair<String[], Double> result =
                    slotMachineGame.play(Mockito.mock(ButtonInteractionEvent.class));
            String[] reels = result.getLeft();
            double payout = result.getRight();
            if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
                double expectedPayout = SYMBOLS.get(reels[0]) * slotMachinePlayer.getBet();
                assertEquals(expectedPayout, payout);
            } else {
                assertEquals(-slotMachinePlayer.getBet(), payout);
            }
        }
    }
}
