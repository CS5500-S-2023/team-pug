package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.IPlayer;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BlackjackPlayerTest {

    private User user;
    private BlackjackPlayer player;
    private IPlayer mockIPlayer;
    private double bet;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn("123");
        Mockito.when(user.getName()).thenReturn("testUser");
        mockIPlayer = Mockito.mock(IPlayer.class);
        bet = 50;
        player =
                new BlackjackPlayer(user) {

                    @Override
                    public boolean canPlay() {
                        return mockIPlayer.canPlay();
                    }

                    @Override
                    public boolean wantPlay() {
                        return mockIPlayer.wantPlay();
                    }
                };
    }

    @Test
    void testCanPlay() {
        Mockito.when(mockIPlayer.canPlay()).thenReturn(true);
        assertTrue(player.canPlay());
        Mockito.when(mockIPlayer.canPlay()).thenReturn(false);
        assertFalse(player.canPlay());
    }

    @Test
    void testWantPlay() {
        Mockito.when(mockIPlayer.wantPlay()).thenReturn(true);
        assertTrue(player.wantPlay());
        Mockito.when(mockIPlayer.wantPlay()).thenReturn(false);
        assertFalse(player.wantPlay());
    }

    @Test
    public void testEquals() {
        BlackjackPlayer player2 =
                new BlackjackPlayer(user) {

                    @Override
                    public boolean canPlay() {
                        return mockIPlayer.canPlay();
                    }

                    @Override
                    public boolean wantPlay() {
                        return mockIPlayer.wantPlay();
                    }
                };
        assertEquals(player, player2);
    }

    @Test
    public void testHashCode() {
        BlackjackPlayer player2 =
                new BlackjackPlayer(user) {

                    @Override
                    public boolean canPlay() {
                        return mockIPlayer.canPlay();
                    }

                    @Override
                    public boolean wantPlay() {
                        return mockIPlayer.wantPlay();
                    }
                };
        assertEquals(player.hashCode(), player2.hashCode());
    }

    @Test
    public void testToString() {
        String expected =
                "BlackjackPlayer(user=edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackPlayerTest$1@3e3abc88, bet=50.0, stop=false)";
        assertNotEquals(expected, player.toString());
    }

    @Test
    public void testClearHand() {
        player.clearHand();
        assertTrue(player.getHand().getCards().isEmpty());
    }

    @Test
    public void testSetHand() {
        Hand newHand = new Hand();
        newHand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        player.setHand(newHand);
        assertEquals(newHand, player.getHand());
    }

    @Test
    public void testSetUser() {
        User newUser = Mockito.mock(User.class);
        player.setUser(newUser);
        assertEquals(newUser, player.getUser());
    }

    @Test
    public void testSetBet() {
        double newBet = 100;
        player.setBet(newBet);
        assertEquals(newBet, player.getBet(), 0.0);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, player.getUser());
    }

    @Test
    public void testGetBet() {
        assertEquals(bet, player.getBet(), 0.0);
    }

    @Test
    public void testCanEqual() {
        BlackjackPlayer player2 =
                new BlackjackPlayer(user) {

                    @Override
                    public boolean canPlay() {
                        return mockIPlayer.canPlay();
                    }

                    @Override
                    public boolean wantPlay() {
                        return mockIPlayer.wantPlay();
                    }
                };
        assertTrue(player.canEqual(player2));
    }
}
