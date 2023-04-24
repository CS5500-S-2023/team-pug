package edu.northeastern.cs5500.starterbot.game;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.blackjack.*;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BlackjackPlayerTest {
    private BlackjackPlayer player;
    private Card card;
    @Mock private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new BlackjackNormalPlayer(user);
        card = new Card(Rank.ACE, Suit.HEARTS);
    }

    @Test
    void testAddCard() {
        player.addCard(card);
        Hand hand = player.getHand();
        assertEquals(1, hand.getCards().size());
        assertEquals(card, hand.getCards().get(0));
    }

    @Test
    void testClearHand() {
        player.addCard(card);
        player.clearHand();
        Hand hand = player.getHand();
        assertTrue(hand.getCards().isEmpty());
    }

    @Test
    void testIsStop() {
        assertFalse(player.isStop());
    }

    @Test
    void testSetStop() {
        player.setStop(true);
        assertTrue(player.isStop());
    }
}
