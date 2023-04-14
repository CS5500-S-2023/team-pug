package edu.northeastern.cs5500.starterbot.game.blackjack;

import lombok.Data;

@Data
public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getValue() {
        return this.rank.getValue();
    }
}