package edu.northeastern.cs5500.starterbot.command;

import static org.junit.jupiter.api.Assertions.*;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.junit.jupiter.api.Test;

public class PlayerInfoCommandTest {
    @Test
    void testGetCommandData() {
        PlayerInfoCommand playerInfoCommand = new PlayerInfoCommand();
        String name = playerInfoCommand.getName();
        CommandData commandData = playerInfoCommand.getCommandData();
        assertEquals(name, commandData.getName());
    }
}
