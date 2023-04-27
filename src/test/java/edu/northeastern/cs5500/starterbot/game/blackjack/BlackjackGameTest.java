package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.Config;
import java.util.List;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BlackjackGameTest {
    @Mock private User mockUser;
    @Mock private User mockUser2;
    @Mock private User mockUser3;
    BlackjackPlayer player1;
    BlackjackPlayer player2;
    BlackjackPlayer player3;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        player1 = new BlackjackNormalPlayer(mockUser);
        player2 = new BlackjackNormalPlayer(mockUser2, 100);
        player3 = new BlackjackNormalPlayer(mockUser3, 300);
    }

    @Test
    public void testCanStart() {

        Config config = new Config("blackjack", 2, 4);
        BlackjackGame game = new BlackjackGame(config, player1);

        assertFalse(game.canStart());
        game.joinPlayer(player2);
        assertTrue(game.canStart());
    }

    @Test
    void initPlayerCard() {
        Config config = new Config("blackjack", 2, 4);
        BlackjackGame game = new BlackjackGame(config, player1);
        game.joinPlayer(player2);
        game.initPlayerCard();

        for (BlackjackPlayer player : game.getPlayers()) {
            assertEquals(2, player.getHand().getCards().size());
        }
    }

    @Test
    void containsId() {
        Config config = new Config("blackjack", 2, 4);
        BlackjackGame game = new BlackjackGame(config, player1);
        game.joinPlayer(player2);
        assertEquals(2, game.getPlayers().size());
        assertTrue(game.getPlayers().contains(player1));
        assertTrue(game.getPlayers().contains(player2));
    }

    @Test
    void hit() {
        Config config = new Config("blackjack", 2, 4);
        BlackjackGame game = new BlackjackGame(config, player1);
        game.joinPlayer(player2);
        game.initPlayerCard();
        game.hit();

        assertEquals(3, player1.getHand().getCards().size());
    }

    @Test
    void stand() {
        Config config = new Config("blackjack", 2, 4);
        BlackjackGame game = new BlackjackGame(config, player1);
        game.joinPlayer(player2);
        game.initPlayerCard();
        game.stand();

        assertTrue(player1.isStop());
    }

    @Test
    void shareAllBets() {
        Config config = new Config("blackjack", 2, 4);
        player1.setBet(200);
        BlackjackGame game = new BlackjackGame(config, player1);
        System.out.println(game.getDealer().getBet());
        player1.addCard(new Card(Rank.TEN, Suit.HEARTS));
        player1.addCard(new Card(Rank.TEN, Suit.HEARTS));
        player1.addCard(new Card(Rank.TEN, Suit.HEARTS));
        game.joinPlayer(player2);
        game.joinPlayer(player3);
        List<Result> result = game.shareAllBets();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getUser(), mockUser);
        assertEquals(result.get(0).getBet(), -200.0, 0.01);
        assertEquals(result.get(1).getUser(), mockUser2);
        assertEquals(result.get(1).getBet(), 150.0, 0.01);
        assertEquals(result.get(2).getUser(), mockUser3);
        assertEquals(result.get(2).getBet(), 450.0, 0.01);
    }
}
