package edu.northeastern.cs5500.starterbot.game.blackjack;
/**
 * An enum representing the rank of a playing card in a standard deck of cards. Each rank has a
 * numeric value and a string value. The string value is used for displaying the card in a graphical
 * user interface.
 *
 * <p>The numeric value of the ACE rank is 11, but it can also be 1 in certain circumstances.
 */
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
    /**
     * Constructs a new instance of the {@code Rank} enum with the specified numeric value and
     * string value.
     *
     * @param value the numeric value of this rank
     * @param pathValue the string value of this rank, used for displaying the card in a graphical
     *     user interface
     */
    private Rank(int value, String pathValue) {
        this.value = value;
        this.pathValue = pathValue;
    }
    /**
     * Returns the numeric value of this rank.
     *
     * @return the numeric value of this rank
     */
    public int getValue() {
        return this.value;
    }
    /**
     * Returns the string value of this rank, used for displaying the card in a graphical user
     * interface.
     *
     * @return the string value of this rank
     */
    public String getPathValue() {
        return this.pathValue;
    }
}
