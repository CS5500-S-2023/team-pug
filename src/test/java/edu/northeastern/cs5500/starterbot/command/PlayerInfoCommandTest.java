package edu.northeastern.cs5500.starterbot.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlayerInfoCommandTest {
    @Test
    void testGetCommandData() {
        PlayerInfoCommand playerInfoCommand = new PlayerInfoCommand();
        String name = playerInfoCommand.getName();
        CommandData commandData = playerInfoCommand.getCommandData();
        assertEquals(name, commandData.getName());
    }

    @Test
    void onSlashCommandInteraction() {
        PlayerInfoCommand playerInfoCommand = new PlayerInfoCommand();
        SlashCommandInteractionEvent slashCommandInteractionEvent =
                mock(SlashCommandInteractionEvent.class);
        User user = mock(User.class);
        ReplyCallbackAction replyCallbackAction = mock(ReplyCallbackAction.class);
        when(slashCommandInteractionEvent.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("123");
        when(slashCommandInteractionEvent.reply(Mockito.anyString()))
                .thenReturn(replyCallbackAction);
        when(replyCallbackAction.setEphemeral(true)).thenReturn(replyCallbackAction);
        PlayerController playerController = new PlayerController(new InMemoryRepository<>());
        playerInfoCommand.playerController = playerController;
        playerInfoCommand.onSlashCommandInteraction(slashCommandInteractionEvent);
    }
}
