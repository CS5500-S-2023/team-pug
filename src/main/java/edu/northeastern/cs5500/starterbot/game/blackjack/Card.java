package edu.northeastern.cs5500.starterbot.game.blackjack;

import lombok.Data;

/**
 * A class representing a card in a standard deck of playing cards. Each card has a {@link Rank} and
 * a {@link Suit}.
 */
@Data
public class Card {
    private Rank rank;
    private Suit suit;

    public Card() {}
    /**
     * Constructs a new instance of the {@code Card} class with the specified rank and suit.
     *
     * @param rank the rank of this card
     * @param suit the suit of this card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    /**
     * Returns the value of this card. The value of a card is determined by its rank.
     *
     * @return the value of this card
     */
    public int getValue() {
        return this.rank.getValue();
    }
}
