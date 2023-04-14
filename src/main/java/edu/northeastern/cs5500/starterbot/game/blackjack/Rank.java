package edu.northeastern.cs5500.starterbot.game.blackjack;

public enum Rank {
    ACE(11, "ace"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "jack"),
    QUEEN(10, "queen"),
    KING(10, "king");

    private final int value;
    private final String pathValue;

    private Rank(int value, String pathValue) {
        this.value = value;
        this.pathValue = pathValue;
    }

    public int getValue() {
        return this.value;
    }

    public String getPathValue() {
        return this.pathValue;
    }
}
