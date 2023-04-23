package edu.northeastern.cs5500.starterbot.game;

import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackNormalPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import net.dv8tion.jda.api.entities.User;

class BlackjackNormalPlayerTest {
    @Mock
    private User user;
    private BlackjackNormalPlayer player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new BlackjackNormalPlayer(user);
    }



    @Test
    void wantPlayReturnsTrueWhenNotStop() {
        player.setStop(false);
        assertTrue(player.wantPlay());
    }

    @Test
    void wantPlayReturnsFalseWhenStop() {
        player.setStop(true);
        assertFalse(player.wantPlay());
    }
}

