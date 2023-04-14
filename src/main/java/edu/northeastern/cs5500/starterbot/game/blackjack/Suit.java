package edu.northeastern.cs5500.starterbot.game.blackjack;

public enum Suit {
    CLUBS("clubs"),
    DIAMONDS("diamonds"),
    HEARTS("hearts"),
    SPADES("spades");

    private final String pathValue;

    private Suit(String pathValue) {
        this.pathValue = pathValue;
    }

    public String getPathValue() {
        return this.pathValue;
    }
}
