package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BlackjackPlayerTest {

    private User user;
    private BlackjackPlayer player;
    private IPlayer mockIPlayer;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn("123");
        Mockito.when(user.getName()).thenReturn("testUser");
        mockIPlayer = Mockito.mock(IPlayer.class);
        player =
                new BlackjackPlayer(user) {

                    @Override
                    public boolean canPlay() {
                        return mockIPlayer.canPlay();
                    }

                    @Override
                    public boolean wantPlay() {
                        return mockIPlayer.wantPlay();
                    }
                };
    }

    @Test
    void testCanPlay() {
        Mockito.when(mockIPlayer.canPlay()).thenReturn(true);
        assertTrue(player.canPlay());
        Mockito.when(mockIPlayer.canPlay()).thenReturn(false);
        assertFalse(player.canPlay());
    }

    @Test
    void testWantPlay() {
        Mockito.when(mockIPlayer.wantPlay()).thenReturn(true);
        assertTrue(player.wantPlay());
        Mockito.when(mockIPlayer.wantPlay()).thenReturn(false);
        assertFalse(player.wantPlay());
    }

    // rest of the test methods
}
