package edu.northeastern.cs5500.starterbot.game.blackjack;
/**
 * An enum representing the suit of a playing card in a standard deck of cards. Each suit has a
 * string value used for displaying the card in a graphical user interface.
 */
public enum Suit {
    CLUBS("clubs"),
    DIAMONDS("diamonds"),
    HEARTS("hearts"),
    SPADES("spades");

    private final String pathValue;
    /**
     * Constructs a new instance of the {@code Suit} enum with the specified string value.
     *
     * @param pathValue the string value of this suit, used for displaying the card in a graphical
     *     user interface
     */
    private Suit(String pathValue) {
        this.pathValue = pathValue;
    }
    /**
     * Returns the string value of this suit, used for displaying the card in a graphical user
     * interface.
     *
     * @return the string value of this suit
     */
    public String getPathValue() {
        return this.pathValue;
    }
}
