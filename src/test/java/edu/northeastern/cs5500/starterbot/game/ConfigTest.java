package edu.northeastern.cs5500.starterbot.game;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigTest {

    @Test
    public void testConstructorWithMinAndMaxPlayers() {
        Config config = new Config("test", 2, 4);
        assertEquals("test", config.getName());
        assertEquals(2, config.getMinPlayers());
        assertEquals(4, config.getMaxPlayers());
    }

    @Test
    public void testConstructorWithNameOnly() {
        Config config = new Config("test");
        assertEquals("test", config.getName());
        assertEquals(1, config.getMinPlayers());
        assertEquals(1, config.getMaxPlayers());
    }

    @Test
    public void testSetName() {
        Config config = new Config("test");
        config.setName("new test");
        assertEquals("new test", config.getName());
    }

    @Test
    public void testSetMinPlayers() {
        Config config = new Config("test");
        config.setMinPlayers(2);
        assertEquals(2, config.getMinPlayers());
    }

    @Test
    public void testSetMaxPlayers() {
        Config config = new Config("test");
        config.setMaxPlayers(4);
        assertEquals(4, config.getMaxPlayers());
    }
}

