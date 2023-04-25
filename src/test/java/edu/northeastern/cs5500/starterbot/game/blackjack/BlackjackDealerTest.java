package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.Test;

public class BlackjackDealerTest {

    @Test
    public void testCanPlay() {
        User dealerUser = mock(User.class);
        BlackjackDealer dealer = new BlackjackDealer(dealerUser);

        // Add cards to dealer's hand until the value is 17 or greater
        while (dealer.getHand().getCurrentValue() < 17) {
            dealer.addCard(new Card(Rank.ACE, Suit.CLUBS));
        }

        // Ensure that dealer.canPlay() returns true
        assertTrue(dealer.canPlay());
    }

    @Test
    public void testWantPlay() {
        // Create a mock user
        User user = mock(User.class);

        // Create a BlackjackDealer and assert that they always want to play
        BlackjackDealer dealer = new BlackjackDealer(user);
        assertTrue(dealer.wantPlay());
    }
}
