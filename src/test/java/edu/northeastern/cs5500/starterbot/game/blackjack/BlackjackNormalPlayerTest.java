package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.Test;

public class BlackjackNormalPlayerTest {

    @Test
    public void testCanPlay() {
        // Create a mock user
        User user = mock(User.class);

        // Create a BlackjackNormalPlayer with an empty hand
        BlackjackNormalPlayer player = new BlackjackNormalPlayer(user);
        assertTrue(player.canPlay());

        // Add a card to the player's hand that puts them over 21
        player.addCard(new Card(Rank.KING, Suit.HEARTS));
        player.addCard(new Card(Rank.KING, Suit.HEARTS));
        player.addCard(new Card(Rank.KING, Suit.HEARTS));
        assertFalse(player.canPlay());
    }

    @Test
    public void testWantPlay() {
        // Create a mock user
        User user = mock(User.class);

        // Create a BlackjackNormalPlayer and assert that they want to play
        BlackjackNormalPlayer player = new BlackjackNormalPlayer(user);
        assertTrue(player.wantPlay());

        // Stop the player and assert that they do not want to play
        player.setStop(true);
        assertFalse(player.wantPlay());
    }
}
