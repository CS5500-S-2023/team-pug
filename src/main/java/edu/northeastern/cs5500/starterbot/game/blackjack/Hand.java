package edu.northeastern.cs5500.starterbot.game.blackjack;

import java.util.List;

public class Hand {
    private List<Card> cards;

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

    public boolean isBust() {
        return this.getCurrentValue() > 21;
    }

    public boolean isBlackjack() {
        return this.cards.size() == 2 && this.getCurrentValue() == 21;
    }

    public void clearCards() {
        cards.clear();
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
