package edu.northeastern.cs5500.starterbot.util;

import edu.northeastern.cs5500.starterbot.game.blackjack.Card;

/**
 * BlackjackUtils is a utility class containing methods for common operations related to the
 * Blackjack game.
 */
public class BlackjackUtils {
    private BlackjackUtils() {}

    /**
     * Maps a Card object to its corresponding image file path.
     *
     * @param card the Card object to be mapped to an image file path.
     * @return a String representing the image file path for the provided card.
     */
    public static String mapCardImageLocation(Card card) {
        String basePath = "/";
        String appendString = "_of_";
        String fileExtension = ".png";
        String rankString = card.getRank().getPathValue();
        String suitString = card.getSuit().getPathValue();
        return basePath + rankString + appendString + suitString + fileExtension;
    }
}
