package edu.northeastern.cs5500.starterbot.util;

import edu.northeastern.cs5500.starterbot.game.blackjack.Card;

public class BlackjackUtils {
    public static String mapCardImageLocation(Card card) {
        String basePath = "/";
        String appendString = "_of_";
        String fileExtension = ".png";
        String rankString = card.getRank().getPathValue();
        String suitString = card.getSuit().getPathValue();
        return basePath + rankString + appendString + suitString + fileExtension;
    }
}
