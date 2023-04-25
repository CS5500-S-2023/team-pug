package edu.northeastern.cs5500.starterbot.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void testPlayerConstructor() {
        String discordUserId = "1234567890";
        Player player = new Player(discordUserId);

        assertEquals("Anonymous", player.getUserName());
        assertEquals(1000.00, player.getBalance(), 0.001);
        assertNotNull(player.getLastLoginTime());
    }

    @Test
    void testSetLastLoginTime() {
        String discordUserId = "1234567890";
        Player player = new Player(discordUserId);
        String previousLoginTime = player.getLastLoginTime();

        // wait for 1 second to ensure the lastLoginTime will be different
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.setLastLoginTime();
        String newLoginTime = player.getLastLoginTime();

        assertNotEquals(previousLoginTime, newLoginTime);
    }
}
