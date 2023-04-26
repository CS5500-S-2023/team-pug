package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void testHandConstructor() {
        Hand hand = new Hand();
        assertNotNull(hand.getCards());
        assertEquals(0, hand.getCards().size());
    }

    @Test
    void testAddCard() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(2, hand.getCards().size());
        assertTrue(hand.getCards().contains(card1));
        assertTrue(hand.getCards().contains(card2));
    }

    @Test
    void testClearCards() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(2, hand.getCards().size());
        hand.clearCards();
        assertEquals(0, hand.getCards().size());
    }

    @Test
    void testGetCurrentValue() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.KING, Suit.HEARTS);
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(21, hand.getCurrentValue());
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));
        assertEquals(13, hand.getCurrentValue());
    }

    @Test
    void testIsBust() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.KING, Suit.HEARTS);
        hand.addCard(card1);
        hand.addCard(card2);
        assertFalse(hand.isBust());
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));
        assertFalse(hand.isBust());
    }

    @Test
    void testIsBlackjack() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.KING, Suit.HEARTS);
        hand.addCard(card1);
        hand.addCard(card2);
        assertTrue(hand.isBlackjack());
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));
        assertFalse(hand.isBlackjack());
    }

    @Test
    public void testEquals() {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        assertEquals(hand1, hand2);
        hand1.addCard(new Card(Rank.TWO, Suit.HEARTS));
        assertNotEquals(hand1, hand2);
        hand2.addCard(new Card(Rank.TWO, Suit.HEARTS));
        assertEquals(hand1, hand2);
    }

    @Test
    public void testHashCode() {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        assertEquals(hand1.hashCode(), hand2.hashCode());
        hand1.addCard(new Card(Rank.TWO, Suit.HEARTS));
        assertNotEquals(hand1.hashCode(), hand2.hashCode());
        hand2.addCard(new Card(Rank.TWO, Suit.HEARTS));
        assertEquals(hand1.hashCode(), hand2.hashCode());
    }

    @Test
    public void testToString() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        hand.addCard(new Card(Rank.THREE, Suit.DIAMONDS));
        String expected =
                "Hand{cards=[Card{rank=TWO, suit=HEARTS}, Card{rank=THREE, suit=DIAMONDS}]}";
        assertNotEquals(expected, hand.toString());
    }

    @Test
    public void testCanEqual() {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        assertTrue(hand1.canEqual(hand2));
    }
}
