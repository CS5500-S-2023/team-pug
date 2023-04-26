package edu.northeastern.cs5500.starterbot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;

public class SetUserNameCommandTest {
    @Test
    void testGetName() {
        SetUserNameCommand setUserNameCommand = new SetUserNameCommand();
        String name = setUserNameCommand.getName();
        CommandData commandData = setUserNameCommand.getCommandData();
        assertEquals(name, commandData.getName());
    }

    @Test
    void testOnSlashCommandInteraction() {
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        OptionMapping op = mock(OptionMapping.class);
        User user = mock(User.class);
        ReplyCallbackAction replyCallbackAction = mock(ReplyCallbackAction.class);
        when(event.getOption("name")).thenReturn(op);
        when(op.getAsString()).thenReturn("testUser");
        when(event.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("123");
        when(event.reply(Mockito.anyString())).thenReturn(replyCallbackAction);
        SetUserNameCommand setUserNameCommand = new SetUserNameCommand();
        PlayerController playerController = new PlayerController(new InMemoryRepository<>());
        setUserNameCommand.playerController = playerController;
        setUserNameCommand.onSlashCommandInteraction(event);
        assertEquals("testUser", setUserNameCommand.playerController.getPlayer("123").getUserName());
    }
}
