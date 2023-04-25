package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testCardConstructor() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertNotNull(card.getRank());
        assertNotNull(card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.SPADES, card.getSuit());
    }

    @Test
    void testGetValue() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        assertEquals(11, card1.getValue());
        Card card2 = new Card(Rank.KING, Suit.HEARTS);
        assertEquals(10, card2.getValue());
        Card card3 = new Card(Rank.FIVE, Suit.CLUBS);
        assertEquals(5, card3.getValue());
    }
}
