package edu.northeastern.cs5500.starterbot.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConfigTest {

    @Test
    void testConfigConstructorWithMinMaxPlayers() {
        String name = "Test Game";
        int minPlayers = 2;
        int maxPlayers = 4;
        Config config = new Config(name, minPlayers, maxPlayers);

        assertEquals(name, config.getName());
        assertEquals(minPlayers, config.getMinPlayers());
        assertEquals(maxPlayers, config.getMaxPlayers());
    }

    @Test
    void testConfigConstructorWithNameOnly() {
        String name = "Test Game";
        Config config = new Config(name);

        assertEquals(name, config.getName());
        assertEquals(1, config.getMinPlayers());
        assertEquals(1, config.getMaxPlayers());
    }

    @Test
    void testConfigSetters() {
        String name = "Test Game";
        int minPlayers = 2;
        int maxPlayers = 4;
        Config config = new Config(name);

        config.setName("New Game Name");
        config.setMinPlayers(minPlayers);
        config.setMaxPlayers(maxPlayers);

        assertEquals("New Game Name", config.getName());
        assertEquals(minPlayers, config.getMinPlayers());
        assertEquals(maxPlayers, config.getMaxPlayers());
    }
}
