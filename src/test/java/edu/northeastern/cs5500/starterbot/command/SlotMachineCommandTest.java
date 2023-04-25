package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SlotMachineCommandTest {

    private SlotMachineCommand slotMachineCommand;
    private SlotMachineController slotMachineController;
    private ButtonInteractionEvent event;
    private Button button;

    private ObjectId gameId;
    private String userId;

    @BeforeEach
    void setUp() {
        gameId = ObjectId.get();
        userId = "123456";
        slotMachineController = Mockito.mock(SlotMachineController.class);
        event = Mockito.mock(ButtonInteractionEvent.class);
        button = Mockito.mock(Button.class);
        slotMachineCommand = new SlotMachineCommand(slotMachineController);

        when(event.getButton()).thenReturn(button);
        when(event.getUser()).thenReturn(mock(net.dv8tion.jda.api.entities.User.class));
    }

    @Test
    void getName() {
        assertEquals(SLOTMACHINE_GAME_NAME, slotMachineCommand.getName());
    }

    @Test
    void onButtonInteractionGameFinished() {
        when(button.getId()).thenReturn(SLOTMACHINE_GAME_NAME + ":PLAY:" + gameId + ":" + userId);
        when(event.getUser().getId()).thenReturn(userId);
        when(slotMachineController.containsGameId(gameId)).thenReturn(false);
        ReplyCallbackAction replyAction = Mockito.mock(ReplyCallbackAction.class);
        InteractionHook hook = Mockito.mock(InteractionHook.class);

        Mockito.when(event.getHook()).thenReturn(hook);
        Mockito.when(event.reply(Mockito.anyString())).thenReturn(replyAction);
        Mockito.when(replyAction.setEphemeral(Mockito.anyBoolean())).thenReturn(replyAction);

        slotMachineCommand.onButtonInteraction(event);

        verify(event).reply("The game has already finished...");
    }

    @Test
    void onButtonInteractionNotYourGame() {
        when(button.getId()).thenReturn(SLOTMACHINE_GAME_NAME + ":PLAY:" + gameId + ":" + userId);
        when(event.getUser().getId()).thenReturn("654321");
        when(slotMachineController.containsGameId(gameId)).thenReturn(true);
        ReplyCallbackAction replyAction = Mockito.mock(ReplyCallbackAction.class);
        InteractionHook hook = Mockito.mock(InteractionHook.class);

        Mockito.when(event.getHook()).thenReturn(hook);
        Mockito.when(event.reply(Mockito.anyString())).thenReturn(replyAction);
        Mockito.when(replyAction.setEphemeral(Mockito.anyBoolean())).thenReturn(replyAction);

        slotMachineCommand.onButtonInteraction(event);

        verify(event).reply("This is not your game...");
    }

    @Test
    void onButtonInteractionValid() {
        when(button.getId()).thenReturn(SLOTMACHINE_GAME_NAME + ":PLAY:" + gameId + ":" + userId);
        when(event.getUser().getId()).thenReturn(userId);
        when(slotMachineController.containsGameId(gameId)).thenReturn(true);
        when(button.getLabel()).thenReturn("PLAY");

        slotMachineCommand.onButtonInteraction(event);

        verify(slotMachineController).handlePlayerAction(gameId, "PLAY", event);
    }
}
