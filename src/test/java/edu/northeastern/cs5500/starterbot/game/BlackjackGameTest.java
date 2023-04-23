package edu.northeastern.cs5500.starterbot.game;
import edu.northeastern.cs5500.starterbot.game.Config;
//import edu.northeastern.cs5500.starterbot.game.Result;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackPlayer;
import edu.northeastern.cs5500.starterbot.game.blackjack.Card;
import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import net.dv8tion.jda.api.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class BlackjackGameTest {

    private BlackjackPlayer holder;
    private BlackjackGame game;

    @Before
    public void setUp() {
        holder = Mockito.mock(BlackjackPlayer.class);
        Mockito.when(holder.getUser()).thenReturn(Mockito.mock(User.class));
        game = new BlackjackGame(new Config("demo"), holder);
    }

    @Test
    public void testInitPlayerCard() {
        BlackjackPlayer player = Mockito.mock(BlackjackPlayer.class);
        List<BlackjackPlayer> players = new ArrayList<>();
        players.add(holder);
        players.add(player);
        game.setPlayers(players);
        game.initPlayerCard();
        Mockito.verify(player, Mockito.times(2)).addCard(Mockito.any(Card.class));
    }

    @Test
    public void testHit() {
        BlackjackPlayer player = Mockito.mock(BlackjackPlayer.class);
        List<BlackjackPlayer> players = new ArrayList<>();
        players.add(holder);
        players.add(player);
        game.setPlayers(players);
        game.hit();
        Mockito.verify(player).addCard(Mockito.any(Card.class));
    }

    @Test
    public void testStand() {
        holder.setStop(false);
        game.stand();
        assertEquals(true, holder.isStop());
    }

    @Test
    public void testShareDealerBets() {
        BlackjackPlayer player1 = Mockito.mock(BlackjackPlayer.class);
        BlackjackPlayer player2 = Mockito.mock(BlackjackPlayer.class);
        List<BlackjackPlayer> players = new ArrayList<>();
        players.add(holder);
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        holder.setBet(10);
        player1.setBet(20);
        player2.setBet(30);
        Mockito.when(player1.canPlay()).thenReturn(true);
        Mockito.when(player2.canPlay()).thenReturn(false);
        List<Result> results = game.shareDealerBets();
        assertEquals(3, results.size());
        assertEquals(-10.0, results.get(0).getBet(), 0.001);
        assertEquals(15.0, results.get(1).getBet(), 0.001);
        assertEquals(25.0, results.get(2).getBet(), 0.001);
    }

    @Test
    public void testShareAllBets() {
        BlackjackPlayer player1 = Mockito.mock(BlackjackPlayer.class);
        BlackjackPlayer player2 = Mockito.mock(BlackjackPlayer.class);
        BlackjackPlayer player3 = Mockito.mock(BlackjackPlayer.class);
        List<BlackjackPlayer> players = new ArrayList<>();
        players.add(holder);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game.setPlayers(players);
        holder.setBet(10);
        player1.setBet(20);
        player2.setBet(30);
        player3.setBet(40);
        Mockito.when(holder.getHand().isBust()).thenReturn(true);
        Mockito.when(player1.getHand().getCurrentValue()).thenReturn(15);
        Mockito.when(player2.getHand().getCurrentValue()).thenReturn(20);
        Mockito.when(player3.getHand().getCurrentValue()).thenReturn(20);
        List<Result> results = game.shareAllBets();
        assertEquals(4, results.size());
        assertEquals(-10.0, results.get(0).getBet(), 0.001);
    }
}
