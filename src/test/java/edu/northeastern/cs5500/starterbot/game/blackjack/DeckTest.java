package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void testDeckConstructor() {
        Deck deck = new Deck();
        assertNotNull(deck.getCards());
        assertEquals(52, deck.getCards().size());
        Set<Card> cardSet = new HashSet<>(deck.getCards());
        assertEquals(52, cardSet.size());
    }

    @Test
    void testShuffle() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck2.shuffle();
        assertNotEquals(deck1.getCards(), deck2.getCards());
    }

    @Test
    void testDeal() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();
        Card card = deck.deal();
        assertNotNull(card);
        assertEquals(initialSize - 1, deck.getCards().size());
    }

    @Test
    void testShuffleDeal() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();
        Card card1 = deck.shuffleDeal();
        Card card2 = deck.shuffleDeal();
        assertNotEquals(card1, card2);
        assertEquals(initialSize - 2, deck.getCards().size());
    }

    @Test
    void testIsEmpty() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());
        for (int i = 0; i < 52; i++) {
            deck.deal();
        }
        assertTrue(deck.isEmpty());
    }

    public void testSetCards() {
        Deck deck = new Deck();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.HEARTS));
        deck.setCards(cards);
        assertEquals(cards, deck.getCards());
    }

    @Test
    public void testCanEqual() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertTrue(deck1.canEqual(deck2));
    }
}
