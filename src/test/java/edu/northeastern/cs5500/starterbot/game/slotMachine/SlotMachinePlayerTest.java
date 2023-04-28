package edu.northeastern.cs5500.starterbot.game.slotMachine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.junit.Before;
import org.junit.Test;

public class SlotMachinePlayerTest {
    private SlotMachinePlayer player;
    private User user;

    @Before
    public void setUp() {
        User user = new UserImpl(1234567890L, null);
        player = new SlotMachinePlayer(user.getId());
    }

    @Test
    public void testCanPlay() {
        assertTrue(player.canPlay());
    }

    @Test
    public void testWantPlay() {
        assertTrue(player.wantPlay());
    }

    @Test
    public void testBet() {
        player.setBet(10.0);
        assertEquals(10.0, player.getBet(), 0.001);
    }
}
