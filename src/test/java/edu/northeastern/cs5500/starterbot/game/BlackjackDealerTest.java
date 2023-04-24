package edu.northeastern.cs5500.starterbot.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackDealer;
import edu.northeastern.cs5500.starterbot.game.blackjack.Hand;
import net.dv8tion.jda.api.entities.User;
import org.junit.Before;
import org.junit.Test;

public class BlackjackDealerTest {

    private BlackjackDealer dealer;
    private User user;

    @Before
    public void setUp() {
        user = mock(User.class);
        dealer = new BlackjackDealer(user);
    }

    @Test
    public void testCanPlay() {
        // hand value is less than 17
        Hand hand = mock(Hand.class);
        when(hand.getCurrentValue()).thenReturn(16);
        dealer.setHand(hand);
        assertTrue(dealer.canPlay());

        // hand value is equal to 17
        hand = mock(Hand.class);
        when(hand.getCurrentValue()).thenReturn(17);
        dealer.setHand(hand);
        assertTrue(dealer.canPlay());

        // hand value is greater than 17
        hand = mock(Hand.class);
        when(hand.getCurrentValue()).thenReturn(18);
        dealer.setHand(hand);
        assertFalse(dealer.canPlay());
    }

    @Test
    public void testWantPlay() {
        assertTrue(dealer.wantPlay());
    }
}
