package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BlackjackNormalPlayerTest {
    @Mock User userMock;

    BlackjackNormalPlayer player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new BlackjackNormalPlayer(userMock);
    }

    @Test
    void canPlay() {
        player.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        player.addCard(new Card(Rank.TWO, Suit.SPADES));
        assertTrue(player.canPlay());

        player.addCard(new Card(Rank.KING, Suit.CLUBS));
        assertFalse(player.canPlay());
    }

    @Test
    void wantPlay() {
        player.setStop(false);
        assertTrue(player.wantPlay());

        // Test with stop = true
        player.setStop(true);
        assertFalse(player.wantPlay());
    }
}
