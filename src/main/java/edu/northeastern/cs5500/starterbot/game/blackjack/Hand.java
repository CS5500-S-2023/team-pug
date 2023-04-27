package edu.northeastern.cs5500.starterbot.game.blackjack;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * A class representing a hand of playing cards in a Blackjack game. This class provides
 * functionality for keeping track of the cards in a hand, computing the value of the hand, and
 * determining whether the hand is a bust or a Blackjack.
 */
@Data
public class Hand {
    private List<Card> cards;
    /** Constructs a new instance of the {@code Hand} class with an empty list of cards. */
    public Hand() {
        cards = new ArrayList<>();
    }
    /**
     * Computes the current value of this hand. Aces have a value of 11 or 1, depending on which
     * value is more beneficial to the hand.
     *
     * @return the current value of this hand
     */
    public int getCurrentValue() {
        int value = 0;
        int numAces = 0;
        for (Card card : this.cards) {
            if (card.getValue() == 11) {
                numAces++;
            }
            value += card.getValue();
        }
        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }
        return value;
    }
    /**
     * Determines whether this hand is a bust. A hand is considered a bust if its value is greater
     * than 21.
     *
     * @return true if this hand is a bust, false otherwise
     */
    public boolean isBust() {
        return this.getCurrentValue() > 21;
    }

    /**
     * Determines whether this hand is a Blackjack. A hand is considered a Blackjack if it consists
     * of exactly two cards and its value is exactly 21.
     *
     * @return true if this hand is a Blackjack, false otherwise
     */
    public boolean isBlackjack() {
        return this.cards.size() == 2 && this.getCurrentValue() == 21;
    }
    /** Clears all cards from this hand. */
    public void clearCards() {
        cards.clear();
    }
    /**
     * Adds the specified card to this hand.
     *
     * @param card the card to add to this hand
     */
    public void addCard(Card card) {
        cards.add(card);
    }
}
