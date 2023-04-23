package edu.northeastern.cs5500.starterbot.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("discordUserId123");
    }

    @Test
    public void testConstructor() {
        assertNotNull(player.getId());
        assertEquals("discordUserId123", player.getDiscordUserId());
        assertEquals("Anonymous", player.getUserName());
        assertEquals(Double.valueOf(1000.00), player.getBalance());
        assertNotNull(player.getLastLoginTime());
    }

    @Test
    public void testSetLastLoginTime() {
        player.setLastLoginTime();
        assertNotNull(player.getLastLoginTime());
    }

    @Test
    public void testSetAndGetId() {
        ObjectId id = new ObjectId();
        player.setId(id);
        assertEquals(id, player.getId());
    }

    @Test
    public void testSetAndGetDiscordUserId() {
        player.setDiscordUserId("newDiscordUserId");
        assertEquals("newDiscordUserId", player.getDiscordUserId());
    }

    @Test
    public void testSetAndGetUserName() {
        player.setUserName("newUserName");
        assertEquals("newUserName", player.getUserName());
    }

    @Test
    public void testSetAndGetBalance() {
        player.setBalance(Double.valueOf(500.00));
        assertEquals(Double.valueOf(500.00), player.getBalance());
    }
}
