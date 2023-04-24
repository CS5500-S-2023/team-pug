package edu.northeastern.cs5500.starterbot.model;

import static org.junit.Assert.assertEquals;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.Game;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackNormalPlayer;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachinePlayer;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.junit.Before;
import org.junit.Test;

public class CasinoTest {

    private Casino casino;
    private List<Game> games;

    @Before
    public void setup() {
        casino = new Casino();
        games = new ArrayList<>();
    }

    @Test
    public void testAddGame() {
        Game game =
                new BlackjackGame(
                        new Config("BlackjackGame"),
                        new BlackjackNormalPlayer(new UserImpl(1L, null)));
        casino.addGame(game);
        games.add(game);
        assertEquals(games, casino.getGames());
    }

    @Test
    public void testRemoveGame() {
        Game game =
                new SlotMachineGame(
                        new Config("SlotMachineGame"),
                        new SlotMachinePlayer(new UserImpl(1L, null)));
        casino.addGame(game);
        casino.removeGame(game);
        assertEquals(games, casino.getGames());
    }
}
