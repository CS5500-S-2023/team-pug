package edu.northeastern.cs5500.starterbot.util;

import edu.northeastern.cs5500.starterbot.game.blackjack.Card;
import edu.northeastern.cs5500.starterbot.game.blackjack.Rank;
import edu.northeastern.cs5500.starterbot.game.blackjack.Suit;
import org.junit.Test;
import static org.junit.Assert.*;


public class BlackjackUtilsTest {

    @Test
    public void testMapCardImageLocation() {
        // Test with a valid card
        Card card = new Card(Rank.KING, Suit.HEARTS);
        String expected = "/king_of_hearts.png";
        String actual = BlackjackUtils.mapCardImageLocation(card);
        assertEquals(expected, actual);

        // Test with another valid card
        card = new Card(Rank.ACE, Suit.SPADES);
        expected = "/ace_of_spades.png";
        actual = BlackjackUtils.mapCardImageLocation(card);
        assertEquals(expected, actual);

        // Test with an invalid card
        card = new Card(null, Suit.DIAMONDS);
        expected = "/null_of_diamonds.png";
        actual = BlackjackUtils.mapCardImageLocation(card);
        assertEquals(expected, actual);
    }

}

