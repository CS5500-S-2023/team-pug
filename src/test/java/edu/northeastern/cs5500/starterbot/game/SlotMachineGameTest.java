package edu.northeastern.cs5500.starterbot.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachinePlayer;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.internal.entities.UserImpl;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class SlotMachineGameTest {
    private SlotMachinePlayer player;
    private SlotMachineGame game;
    private Config config;

    @Before
    public void setUp() {
        player = new SlotMachinePlayer(new UserImpl(1L,null));
        config = new Config("slot_machine");
        game = new SlotMachineGame(config, player);
    }

    @Test
    public void testPlay() {
        ButtonInteractionEvent event = mock(ButtonInteractionEvent.class);
        when(event.getButton().getId()).thenReturn("spin_button");
        Pair<String[], Double> result = game.play(event);
        assertTrue(result.getLeft().length == 3);
        assertTrue(result.getRight() >= 0);
    }




}
