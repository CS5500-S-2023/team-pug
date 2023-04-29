package edu.northeastern.cs5500.starterbot.listener;

import static org.mockito.Mockito.*;

import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import edu.northeastern.cs5500.starterbot.command.ModalHandler;
import edu.northeastern.cs5500.starterbot.command.SlashCommandHandler;
import java.util.HashSet;
import java.util.Set;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MessageListenerTest {
    private Set<SlashCommandHandler> slashCommandHandlers;
    private Set<ButtonHandler> buttonHandlers;
    private Set<ModalHandler> modalHandlers;

    private MessageListener messageListener;

    @BeforeEach
    public void setUp() {
        slashCommandHandlers = new HashSet<>();
        buttonHandlers = new HashSet<>();
        modalHandlers = new HashSet<>();

        messageListener = new MessageListener();
        messageListener.commands = slashCommandHandlers;
        messageListener.buttons = buttonHandlers;
        messageListener.modals = modalHandlers;
    }

    @Test
    public void testOnSlashCommandInteraction() {
        SlashCommandHandler mockHandler = Mockito.mock(SlashCommandHandler.class);
        when(mockHandler.getName()).thenReturn("testcommand");
        slashCommandHandlers.add(mockHandler);

        SlashCommandInteractionEvent mockEvent = Mockito.mock(SlashCommandInteractionEvent.class);
        when(mockEvent.getName()).thenReturn("testcommand");

        messageListener.onSlashCommandInteraction(mockEvent);

        verify(mockHandler, times(1)).onSlashCommandInteraction(mockEvent);
    }

    @Test
    public void testOnButtonInteraction() {
        ButtonHandler mockHandler = Mockito.mock(ButtonHandler.class);
        when(mockHandler.getName()).thenReturn("testbutton");
        buttonHandlers.add(mockHandler);

        Button mockButton = Mockito.mock(Button.class);
        ButtonInteractionEvent mockEvent = Mockito.mock(ButtonInteractionEvent.class);
        when(mockEvent.getButton()).thenReturn(mockButton);
        when(mockButton.getId()).thenReturn("testbutton:someid");

        messageListener.onButtonInteraction(mockEvent);

        verify(mockHandler, times(1)).onButtonInteraction(mockEvent);
    }

    @Test
    public void testOnModalInteraction() {
        ModalHandler mockHandler = Mockito.mock(ModalHandler.class);
        when(mockHandler.getName()).thenReturn("testmodal");
        modalHandlers.add(mockHandler);

        ModalInteractionEvent mockEvent = Mockito.mock(ModalInteractionEvent.class);
        when(mockEvent.getModalId()).thenReturn("testmodal:someid");

        messageListener.onModalInteraction(mockEvent);

        verify(mockHandler, times(1)).onModalInteraction(mockEvent);
    }
}
