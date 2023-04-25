package edu.northeastern.cs5500.starterbot.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("1234567890");
    }

    @Test
    void testPlayerConstructor() {
        String discordUserId = "1234567890";
        Player player = new Player(discordUserId);

        assertEquals("Anonymous", player.getUserName());
        assertEquals(1000.00, player.getBalance(), 0.001);
        assertNotNull(player.getLastLoginTime());
    }

    @Test
    void testEquals() {
        Player player2 = new Player("1234567890");
        assertTrue(player.equals(player2));
    }

    @Test
    void testHashCode() {
        Player player2 = new Player("1234567890");
        assertEquals(player.hashCode(), player2.hashCode());
    }

    @Test
    void testToString() {
        String expected =
                "Player(id=null, discordUserId=1234567890, userName=Anonymous, balance=1000.0, lastLoginTime="
                        + player.getLastLoginTime()
                        + ")";
        assertEquals(expected, player.toString());
    }

    @Test
    void testSetDiscordUserId() {
        player.setDiscordUserId("0987654321");
        assertEquals("0987654321", player.getDiscordUserId());
    }

    @Test
    void testSetLastLoginTime() throws ParseException {
        String newDate = "2023.04.25.12.30.00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        java.util.Date date = format.parse(newDate);
        long time = date.getTime();
        player.setLastLoginTime();
        assertNotEquals(newDate, player.getLastLoginTime());
    }

    @Test
    void testGetDiscordUserId() {
        assertEquals("1234567890", player.getDiscordUserId());
    }

    @Test
    void testCanEqual() {
        Player player2 = new Player("1234567890");
        assertTrue(player.canEqual(player2));
    }
}
