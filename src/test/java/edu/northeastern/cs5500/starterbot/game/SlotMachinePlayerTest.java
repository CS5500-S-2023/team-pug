package edu.northeastern.cs5500.starterbot.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachinePlayer;
import net.dv8tion.jda.api.entities.User;
import org.junit.Before;
import org.junit.Test;

public class SlotMachinePlayerTest {
    private SlotMachinePlayer player;
    private User user;

    @Before
    public void setUp() {
        user = mock(User.class);
        player = new SlotMachinePlayer(user);
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
    public void testUser() {
        assertEquals(user, player.getUser());
    }

    @Test
    public void testBet() {
        player.setBet(10.0);
        assertEquals(10.0, player.getBet(), 0.001);
    }
}

