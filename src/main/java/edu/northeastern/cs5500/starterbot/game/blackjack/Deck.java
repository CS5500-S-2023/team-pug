package edu.northeastern.cs5500.starterbot.game.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import lombok.Data;

@Data
public class Deck {
    private List<Card> cards;
    private Random random;
    @Inject
    public Deck() {
        this.cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(rank, suit));
            }
        }
        this.random = new Random();
    }

    public void shuffle() {
        for (int i = this.cards.size() - 1; i > 0; i--) {
            int j = this.random.nextInt(i + 1);
            Card temp = this.cards.get(i);
            this.cards.set(i, this.cards.get(j));
            this.cards.set(j, temp);
        }
    }

    public Card deal() {
        return this.cards.remove(0);
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
}
