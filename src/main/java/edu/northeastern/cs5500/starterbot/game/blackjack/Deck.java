package edu.northeastern.cs5500.starterbot.game.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Data;

/**
 * A class representing a deck of standard playing cards. Each deck contains 52 cards, with 13 ranks
 * and 4 suits. This class provides functionality for shuffling and dealing cards from the deck.
 */
@Data
public class Deck {
    private List<Card> cards;

    /** Constructs a new instance of the {@code Deck} class with a full deck of 52 cards. */
    public Deck() {
        this.cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(rank, suit));
            }
        }
    }

    /** Shuffles the cards in this deck using the Fisher-Yates shuffle algorithm. */
    public void shuffle() {
        for (int i = this.cards.size() - 1; i > 0; i--) {
            int j = new Random().nextInt(i + 1);
            Card temp = this.cards.get(i);
            this.cards.set(i, this.cards.get(j));
            this.cards.set(j, temp);
        }
    }

    /**
     * Deals the top card from this deck.
     *
     * @return the top card from this deck
     */
    public Card deal() {
        return this.cards.remove(0);
    }

    /**
     * Shuffles the cards in this deck and deals the top card.
     *
     * @return the top card from a shuffled deck
     */
    public Card shuffleDeal() {
        shuffle();
        return deal();
    }

    /**
     * Determines whether this deck is empty.
     *
     * @return true if this deck is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
}
