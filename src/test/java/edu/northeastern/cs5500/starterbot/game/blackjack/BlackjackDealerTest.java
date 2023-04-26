package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BlackjackDealerTest {
    @Mock User mockUser;

    BlackjackDealer dealer;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        dealer = new BlackjackDealer(mockUser);
    }

    @Test
    void canPlay() {
        dealer.addCard(new Card(Rank.TEN, Suit.CLUBS));
        dealer.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        assertTrue(dealer.canPlay());

        dealer.addCard(new Card(Rank.SEVEN, Suit.DIAMONDS));
        assertFalse(dealer.canPlay());
    }

    @Test
    void wantPlay() {
        assertTrue(dealer.wantPlay());
    }
}
