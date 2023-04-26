package edu.northeastern.cs5500.starterbot.controller;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SlotMachineControllerTest {

    private SlotMachineController slotMachineController;
    private GenericRepository<SlotMachineGame> slotMachineRepository;
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        slotMachineRepository = new InMemoryRepository<>();
        playerController = Mockito.mock(PlayerController.class);
        slotMachineController = new SlotMachineController(slotMachineRepository, playerController);
    }

    @Test
    void testNewGame_createsNewGameAndAddsToRepository() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = slotMachineController.newGame(user);
        assertNotNull(gameId);

        SlotMachineGame game = slotMachineRepository.get(gameId);
        assertNotNull(game);
        assertEquals(user, game.getCurrentPlayer().getUser());
    }

    @Test
    void testGetGameFromObjectId_returnsCorrectGame() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = slotMachineController.newGame(user);

        SlotMachineGame game = slotMachineController.getGameFromObjectId(gameId);
        assertNotNull(game);
        assertEquals(gameId, game.getId());
    }

    @Test
    void testGetGameFromObjectId_returnsNullForNonexistentGame() {
        ObjectId nonExistentGameId = new ObjectId();
        assertNull(slotMachineController.getGameFromObjectId(nonExistentGameId));
    }

    @Test
    void testContainsGameId_returnsTrueForExistingGame() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = slotMachineController.newGame(user);

        assertTrue(slotMachineController.containsGameId(gameId));
    }

    @Test
    void testContainsGameId_returnsFalseForNonexistentGame() {
        ObjectId nonExistentGameId = new ObjectId();
        assertFalse(slotMachineController.containsGameId(nonExistentGameId));
    }

    @Test
    void testUpdateBalance_callsPlayerControllerUpdateBalance() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn("test_user_id");
        double betAmount = 100.0;

        Result result = new Result(user, betAmount);
        slotMachineController.updateBalance(result);

        Mockito.verify(playerController, Mockito.times(1)).updateBalance(user.getId(), betAmount);
    }

    @Test
    void testHandlePlayerAction_play() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = slotMachineController.newGame(user);
        ButtonInteractionEvent event = Mockito.mock(ButtonInteractionEvent.class);
        ReplyCallbackAction replyAction = Mockito.mock(ReplyCallbackAction.class);
        InteractionHook hook = Mockito.mock(InteractionHook.class);

        // Mock the getId method to return a non-null string value
        Mockito.when(user.getId()).thenReturn("user-id-123");

        Mockito.when(event.getHook()).thenReturn(hook);
        Mockito.when(event.reply(Mockito.anyString())).thenReturn(replyAction);
        Mockito.when(replyAction.setEphemeral(Mockito.anyBoolean())).thenReturn(replyAction);

        // Simulate sendMessage returning null
        Mockito.when(hook.sendMessage(Mockito.any(MessageCreateData.class))).thenReturn(null);

        // You can either expect an exception to be thrown or test for specific handling of the null
        // case
        assertThrows(
                Exception.class,
                () -> slotMachineController.handlePlayerAction(gameId, "PLAY", event));

        Mockito.verify(event, Mockito.times(1)).reply(Mockito.anyString());
        Mockito.verify(playerController, Mockito.times(1))
                .updateBalance(Mockito.anyString(), Mockito.anyDouble());
    }

    @Test
    void testHandlePlayerAction_end() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = slotMachineController.newGame(user);
        ButtonInteractionEvent event = Mockito.mock(ButtonInteractionEvent.class);
        ReplyCallbackAction replyAction = Mockito.mock(ReplyCallbackAction.class);
        InteractionHook hook = Mockito.mock(InteractionHook.class);

        // Mock the getId method to return a non-null string value
        Mockito.when(user.getId()).thenReturn("user-id-123");

        Mockito.when(event.getHook()).thenReturn(hook);
        Mockito.when(event.reply(Mockito.anyString())).thenReturn(replyAction);
        Mockito.when(replyAction.setEphemeral(Mockito.anyBoolean())).thenReturn(replyAction);

        // Simulate sendMessage returning null
        Mockito.when(hook.sendMessage(Mockito.any(MessageCreateData.class))).thenReturn(null);
        // You can either expect an exception to be thrown or test for specific handling of the null
        // case
        assertThrows(
                Exception.class,
                () -> slotMachineController.handlePlayerAction(gameId, "END", event));

        // Mockito.verify(hook, Mockito.times(1)).sendMessage(Mockito.any(MessageCreateData.class));
        assertFalse(slotMachineController.containsGameId(gameId));
    }
}
