package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

    @Test
    public void testEquals() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertFalse(deck1.equals(deck2));

        deck1.shuffle();
        assertFalse(deck1.equals(deck2));
    }

    @Test
    public void testHashCode() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertNotEquals(deck1.hashCode(), deck2.hashCode());

        deck1.shuffle();
        assertNotEquals(deck1.hashCode(), deck2.hashCode());
    }

    @Test
    public void testSetCards() {
        Deck deck = new Deck();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.HEARTS));
        deck.setCards(cards);
        assertEquals(cards, deck.getCards());
    }

    @Test
    public void testSetRandom() {
        Deck deck = new Deck();
        Random random = new Random();
        deck.setRandom(random);
        assertEquals(random, deck.getRandom());
    }

    @Test
    public void testGetRandom() {
        Deck deck = new Deck();
        assertNotNull(deck.getRandom());
    }

    @Test
    public void testCanEqual() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertTrue(deck1.canEqual(deck2));
    }
}
